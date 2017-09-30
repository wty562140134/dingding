package com.youtong.dingding.controller.IsvReceiv

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletInputStream;

import com.alibaba.fastjson.JSONObject;
import com.youtong.dingding.controller.IsvReceiv.IsvEnv.Env;
import com.youtong.dingding.controller.IsvReceiv.IsvEnv.FileUtils;
import com.youtong.dingding.controller.IsvReceiv.IsvEnv.HttpHelper;
import com.youtong.dingding.controller.IsvReceiv.IsvEnv.utils.DingTalkEncryptException;
import com.youtong.dingding.controller.IsvReceiv.IsvEnv.utils.DingTalkEncryptor;
import com.youtong.dingding.controller.IsvReceiv.IsvReceivService.ServiceHelper;
import com.youtong.dingding.interfaces.sendgetImp.SendImp;
import com.youtong.dingding.interfaces.sendgetImp.resObj.resObj;


import oracle.net.ano.SupervisorService;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

/**
 *
 * des：实现目标如下： 1、创建套件时的回调url验证有效性 2、回调事件的处理
 *
 * suite_ticket：推送过来的，二十分钟一次，套件的票，用来得到套件的访问令牌suiteAccessToken
 *
 * suiteAccessToken：套件的访问令牌
 *
 * tmp_auth_code:推送过来的，临时授权码，使用此授权码加套件的访问令牌（suiteAccessToken）可以拿到企业永久的授权码（
 * permanent_code）和用户corpid
 *
 * 通过permanent_code corpid suiteAccessToken获取企业用户的accessToken
 *
 * 拿到accessToken就可以去调用相关的接口信息了
 *
 * */
class IsvReceivController extends Controller {

	private def resObj = new resObj()

	def index(){
		try {
			/** 获取url中的签名，时间戳，随机字符串**/
			def infoData = getParaMap()
			//			def signature = getPara("signature")
			//			def timestamp = getPara("timestamp")
			//			def nonce = getPara("nonce")


			/** post数据包数据中的加密数据 **/
			def encrypt = HttpKit.readData(getRequest())

			/*
			 * post数据包数据中的加密数据转换成JSON对象，JSON对象的格式如下 { "encrypt":
			 * "1ojQf0NSvw2WPvW7LijxS8UvISr8pdDP+rXpPbcLGOmIBNbWetRg7IP0vdhVgkVwSoZBJeQwY2zhROsJq/HJ+q6tp1qhl9L1+ccC9ZjKs1wV5bmA9NoAWQiZ+7MpzQVq+j74rJQljdVyBdI/dGOvsnBSCxCVW0ISWX0vn9lYTuuHSoaxwCGylH9xRhYHL9bRDskBc7bO0FseHQQasdfghjkl"
			 * }
			 */
			this.resObj.setJsonStr(encrypt)

			/** 取得JSON对象中的encrypt字段， **/
			encrypt = this.resObj.getJsonData("encrypt")

			/** 对encrypt进行解密 **/
			def dingTalkEncryptor = null;
			def plainText = null;
			def dingTalkEncryptException = null;
			try {
				/*
				 * 创建加解密类 第一个参数为注册套件的之时填写的token
				 * 第二个参数为注册套件的之时生成的数据加密密钥（ENCODING_AES_KEY）
				 * 第三个参数，ISV开发传入套件的suiteKey，普通企业开发传Corpid
				 * 具体参数值请查看开发者后台(http://console.d.aliyun.com)
				 * 
				 * 注：其中，对于第三个参数，在第一次接受『验证回调URL有效性事件的时候』
				 * 传入Env.CREATE_SUITE_KEY，对于这种情况，已在异常中catch做了处理。
				 * 具体区别请查看文档『验证回调URL有效性事件』
				 */
				dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN,
						Env.ENCODING_AES_KEY, Env.SUITE_KEY);
				/*
				 * 获取从encrypt解密出来的明文
				 */
				plainText = dingTalkEncryptor.getDecryptMsg(infoData.signature[0],
						infoData.timestamp[0], infoData.nonce[0], encrypt);
			} catch (DingTalkEncryptException e) {
				// TODO Auto-generated catch block
				dingTalkEncryptException = e;
				e.printStackTrace();
			} finally {
				if (dingTalkEncryptException != null) {
					if (dingTalkEncryptException.code == DingTalkEncryptException.COMPUTE_DECRYPT_TEXT_CORPID_ERROR) {
						try {
							/*
							 * 第一次创建套件生成加解密类需要传入Env.CREATE_SUITE_KEY，
							 */
							dingTalkEncryptor = new DingTalkEncryptor(
									Env.TOKEN, Env.ENCODING_AES_KEY,
									Env.CREATE_SUITE_KEY);
							plainText = dingTalkEncryptor.getDecryptMsg(
									infoData.signature[0], infoData.timestamp[0], infoData.nonce[0], encrypt);
						} catch (DingTalkEncryptException e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					} else {
						System.out.println(dingTalkEncryptException
								.getMessage());
						dingTalkEncryptException.printStackTrace();
					}
				}
			}

			/*
			 * 对从encrypt解密出来的明文进行处理 不同的eventType的明文数据格式不同
			 */
			this.resObj.resetJsonStr(plainText);
			def eventType = this.resObj.getJsonData("EventType")
			/*
			 * res是需要返回给钉钉服务器的字符串，一般为success
			 * "check_create_suite_url"和"check_update_suite_url"事件为random字段
			 * 具体请查看文档或者对应eventType的处理步骤
			 */
			def res = "success";

			switch (eventType) {
				case "suite_ticket":
				/*
				 * "suite_ticket"事件每二十分钟推送一次,数据格式如下 { "SuiteKey": "suitexxxxxx",
				 * "EventType": "suite_ticket ", "TimeStamp": 1234456,
				 * "SuiteTicket": "adsadsad" }
				 */
					Env.suiteTicket = this.resObj.getJsonData("SuiteTicket");
				// 获取到suiteTicket之后需要换取suiteToken，
					Env.suiteToken = ServiceHelper.getSuiteToken(Env.SUITE_KEY,
							Env.SUITE_SECRET, Env.suiteTicket);
					print "suite_ticket resObj====>"+this.resObj.getJsonObj()
				/*
				 * ISV应当把最新推送的suiteTicket做持久化存储， 以防重启服务器之后丢失了当前的suiteTicket
				 */
					break;
				case "tmp_auth_code":
				/*
				 * "tmp_auth_code"事件将企业对套件发起授权的时候推送 数据格式如下 { "SuiteKey":
				 * "suitexxxxxx", "EventType": " tmp_auth_code", "TimeStamp":
				 * 1234456, "AuthCode": "adads" }
				 */
					Env.authCode = this.resObj.getJsonData("AuthCode");
					print "tmp_auth_code resObj======>"+this.resObj.getJsonObj()
					if (Env.suiteToken == null) {
						break;
					}
				/*
				 * 拿到tmp_auth_code（临时授权码）后，需要向钉钉服务器获取企业的corpId（企业id）和permanent_code
				 * （永久授权码）
				 */


					print ServiceHelper.getPermanentCode(Env.authCode, Env.suiteToken);
				//					String corpId =
				//							corpAuthSuiteCode.getAuth_corp_info().getCorpid();
				//					String permanent_code =
				//							corpAuthSuiteCode.getPermanent_code();
				/*
				 * 将corpId（企业id）和permanent_code（永久授权码）做持久化存储
				 * 之后在获取企业的access_token时需要使用
				 */
				// JSONObject jsonPerm = new JSONObject();
				// jsonPerm.put(corpId, permanent_code);
				// FileUtils.write2File(jsonPerm, "permanentcode");
				/*
				 * 对企业授权的套件发起激活，
				 */
				// ServiceHelper.getActivateSuite(suiteTokenPerm, Env.SUITE_KEY,
				// corpId, permanent_code);
				/*
				 * 获取对应企业的access_token，每一个企业都会有一个对应的access_token，
				 * 访问对应企业的数据都将需要带上这个access_token access_token的过期时间为两个小时
				 */
				// try {
				// AuthHelper.getAccessToken(corpId);
				//
				// } catch (Exception e1) {
				// // TODO Auto-generated catch block
				// System.out.println(e1.toString());
				// e1.printStackTrace();
				// }
					break;
				case "change_auth":
				/*
				 * "change_auth"事件将在企业授权变更消息发生时推送 数据格式如下 { "SuiteKey":
				 * "suitexxxxxx", "EventType": " change_auth", "TimeStamp":
				 * 1234456, "AuthCorpId": "xxxxx" }
				 */

					def corpid = this.resObj.getJsonData("AuthCorpID");
					print "change_auth resObj=======>"+ this.resObj.getJsonObj()
				// 由于以下操作需要从持久化存储中获得数据，而本demo并没有做持久化存储（因为部署环境千差万别），所以没有具体代码，只有操作指导。
				// 1.根据corpid查询对应的permanent_code(永久授权码)
				// 2.调用『企业授权的授权数据』接口（ServiceHelper.getAuthInfo方法），此接口返回数据具体详情请查看文档。
				// 3.遍历从『企业授权的授权数据』接口中获取所有的微应用信息
				// 4.对每一个微应用都调用『获取企业的应用信息接口』（ServiceHelper.getAgent方法）
				/*
				 * 5.获取『获取企业的应用信息接口』接口返回值其中的"close"参数，才能得知微应用在企业用户做了授权变更之后的状态，有三种状态码
				 * 分别为0，1，2.含义如下： 0:禁用（例如企业用户在OA后台禁用了微应用） 1:正常
				 * (例如企业用户在禁用之后又启用了微应用) 2:待激活 (企业已经进行了授权，但是ISV还未为企业激活应用)
				 * 再根据具体状态做具体操作。
				 */

					break;
				case "check_create_suite_url":
				/*
				 * "check_create_suite_url"事件将在创建套件的时候推送 {
				 * "EventType":"check_create_suite_url", "Random":"brdkKLMW",
				 * "TestSuiteKey":"suite4xxxxxxxxxxxxxxx" }
				 */
				// 此事件需要返回的"Random"字段，
					res = this.resObj.getJsonData("Random");
					def testSuiteKey = this.resObj.getJsonData("TestSuiteKey");
					print "check_create_suite_url resObj======>"+this.resObj.getJsonObj()
					break;

				case "check_update_suite_url":
				/*
				 * "check_update_suite_url"事件将在更新套件的时候推送 {
				 * "EventType":"check_update_suite_url", "Random":"Aedr5LMW",
				 * "TestSuiteKey":"suited6db0pze8yao1b1y"
				 * 
				 * }
				 */
					res = this.resObj.getJsonData("Random");
					print "check_update_suite_url resObj======>"+this.resObj.getJsonObj()
					break;
				default: // do something
					break;
			}

			/** 对返回信息进行加密 **/
			def timeStampLong = Long.parseLong(infoData.timestamp[0]);
			def jsonMap = [:]
			try {
				/*
				 * jsonMap是需要返回给钉钉服务器的加密数据包
				 */
				jsonMap = dingTalkEncryptor.getEncryptedMap(res, timeStampLong,
						infoData.nonce[0]);
			} catch (DingTalkEncryptException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			this.resObj.resetJsonMap(jsonMap)
			renderJson(this.resObj.getJsonObj().toString())
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
}
