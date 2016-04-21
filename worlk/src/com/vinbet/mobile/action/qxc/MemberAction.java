package com.vinbet.mobile.action.qxc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vinbet.mobile.domain.QxcUser;
import com.vinbet.mobile.enums.QxcHandicap;
import com.vinbet.mobile.sys.AppCache;
import com.vinbet.mobile.sys.AppStatusCheckFilter;
import com.vinbet.mobile.sys.PropertiesLoader;
import com.vinbet.mobile.util.ArithUtil;
import com.vinbet.mobile.util.BASE64Util;
import com.vinbet.mobile.util.CheckSession;
import com.vinbet.mobile.util.Constants;
import com.vinbet.mobile.util.DateUtil;
import com.vinbet.mobile.util.HttpClientUtil;
import com.vinbet.mobile.util.JsonResult;
import com.vinbet.mobile.util.MD5Util;

@InjectName("qxcMemberAction")
@IocBean(singleton = false)
@Filters({ @By(type = CheckSession.class, args = { "QXC_USER_SESSION", "/timeout" }), @By(type = AppStatusCheckFilter.class) })
@At("/qxc")
public class MemberAction {
	private Log log = Logs.getLog(MemberAction.class);
	@Inject
	private PropertiesLoader propertiesLoader;

	/**
	 * 跳转到下注明细页面
	 */
	@At("/betInfo")
	@Ok("jsp:page.qxc.user.betInfo")
	public void toBetInfo() {
	}

	/**
	 * 跳转到历史明细
	 */
	@At("/historyDetail")
	@Ok("jsp:page.qxc.user.historyDetail")
	public Object toHistoryDetail(String issue) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("issue", issue);
		return obj;
	}

	/**
	 * 获取下注状况数据(包括历史明细数据)
	 * 
	 * @param session
	 * @return
	 */
	@At("/getBetInfo")
	@Ok("json")
	public JsonResult getBetInfo(String gid, int cp, String status, boolean first, String issue, HttpServletRequest request, HttpSession session) {
		JsonResult jsonResult = JsonResult.createSuccess();
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/orderinfo").toString();
		QxcUser hkcUser = (QxcUser) session.getAttribute(com.vinbet.mobile.util.Constants.QXC_USER_SESSION);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("game", gid);// 玩法code
		parameters.put("status", status);// 状态
		parameters.put("targetpage", String.valueOf(cp));// 页码
		parameters.put("name", hkcUser.getAccoutName());
		if (issue != null && !issue.equals("")) {
			parameters.put("issue", issue);
		}
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		String response = HttpClientUtil.doGetString(url, parameters);
		// String response =
		// "{'code':'10000','description':'success','targetpage':1,'pagesize':20,'pagecount':1,'issue':'2014148','game':'','playList':[{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387886,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabed2','gameName':'特码','gameCode':'10','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null, 'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387883,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabed5','gameName':'正码','gameCode':'11','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue ':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387880,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabed8','gameName':'正码特','gameCode':'12','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize ':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387873,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabedf','gameName':'一肖','gameCode':'13','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond ':1388817348,'inc':-152387872,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabee0','gameName':'尾数','gameCode':'14','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387871,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabee1 ','gameName':'连码','gameCode':'15','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387865,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabee7','gameName':'六肖','gameCode':'16','status':null,'sort':null, 'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387859,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabeed','gameName':'过关','gameCode':'17','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level ':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387858,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabeee','gameName':'特肖','gameCode':'18','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null, 'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387857,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabeef','gameName':'特码头尾','gameCode':'19','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage ':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387856,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabef0','gameName':'五行','gameCode':'20','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656, 'timeSecond':1388817348,'inc':-152387855,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabef1','gameName':'半波','gameCode':'21','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387854,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabef2','gameName':'不中','gameCode':'22','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387847,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabef9','gameName':'生肖连','gameCode':'23','status':null,'sort ':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387840,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabf00','gameName':'尾数连','gameCode':'24','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type': '1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387833,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabf07','gameName':'一肖不中','gameCode':'27','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag ':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387832,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabf08','gameName':'尾数不中','gameCode':'28','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0, 'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387831,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabf09','gameName':'七码','gameCode':'29','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id ':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387830,'time':1388817348000,'new':false},'pid':'52c7abc4ba091220f6eabf0a','gameName':'多选中一','gameCode':'30','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-1173810656,'timeSecond':1388817348,'inc':-152387823,'time':1388817348000, 'new':false},'pid':'52c7abc4ba091220f6eabf11','gameName':'特平中','gameCode':'31','status':null,'sort':null,'muserId':null,'modifier':null,'updateTime':null,'ip':null,'type':'1','level':null,'pcContorl':null,'isSealHp':null,'parentGc':null,'frontFlag':null,'avalue':null,'bvalue':'','url':null}],'status':'1','openBall':null,'animal':null,'orderList':[{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458218733,'timeSecond':1414335550,'inc':1196693654,'time':1414335550000,'new':false}, 'pid':'544d0c3ee4b0231347541896','userId':null,'userName':'testkang','hcType':'4','gameCode':'1101','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'01','rateZh':null,'rate':8.12,'rate2':0.0,'betAmount':23.0,'frontAmount':null,'backAmount':null,'mayAmount':163.76,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414335550327,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1, 'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage ':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458193028,'timeSecond':1414311902,'inc':-803187550,'time':1414311902000,'new':false},'pid':'544cafdee4b0877cd02054a2','userId':null,'userName':'testkang','hcType':'4','gameCode':'1501','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'11,16,17,19,23,26,28,29','rateZh':'726,726,726,726,726,726,726,726','rate':0.0,'rate2':0.0,'betAmount':1120.0,'frontAmount':null,'backAmount':null,'mayAmount':812000.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null, 'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414311902485,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType ':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':'20141026162502544c970ae4b0ce15fb1aa5a0892301','bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458218733,'timeSecond':1414310223,'inc':1196693651,'time':1414310223000,'new':false},'pid':'544ca94fe4b0231347541893','userId':null,'userName':'testkang','hcType':'4','gameCode':'1001','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'01','rateZh':null, 'rate':47.0,'rate2':0.0,'betAmount':30.0,'frontAmount':null,'backAmount':null,'mayAmount':1380.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414310223791,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted ':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458193028,'timeSecond':1414307004,'inc':-803187557,'time':1414307004000,'new':false},'pid':'544c9cbce4b0877cd020549b','userId':null,'userName ':'testkang','hcType':'4','gameCode':'1503','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'11#14','rateZh':null,'rate':71.0,'rate2':0.0,'betAmount':23.0,'frontAmount':null,'backAmount':null,'mayAmount':1610.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414307004061,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null, 'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version ':null,'id':{'machine':-458193028,'timeSecond':1414307004,'inc':-803187556,'time':1414307004000,'new':false},'pid':'544c9cbce4b0877cd020549c','userId':null,'userName':'testkang','hcType':'4','gameCode':'1503','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'12#14','rateZh':null,'rate':71.0,'rate2':0.0,'betAmount':23.0,'frontAmount':null,'backAmount':null,'mayAmount':1610.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo ':null,'betTime':1414307004061,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber ':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458193028,'timeSecond':1414307004,'inc':-803187558,'time':1414307004000,'new':false},'pid':'544c9cbce4b0877cd020549a','userId':null,'userName':'testkang','hcType':'4','gameCode':'1503','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'11#12','rateZh':null,'rate':71.0,'rate2':0.0,'betAmount':23.0,'frontAmount':null,'backAmount':null,'mayAmount':1610.0,'backCommision':0.0, 'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414307004060,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType ':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458218733,'timeSecond':1414306992,'inc':1196693647,'time':1414306992000,'new':false},'pid':'544c9cb0e4b023134754188f','userId':null,'userName':'testkang','hcType':'4','gameCode':'1201','pgameCode':null,'ggameCode':'','issue': '2014148','betBall':'11','rateZh':null,'rate':48.89999923706055,'rate2':0.0,'betAmount':100.0,'frontAmount':null,'backAmount':null,'mayAmount':4789.999923706055,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414306992273,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null, 'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458218733,'timeSecond':1414306983,'inc':1196693643,'time':1414306983000,'new ':false},'pid':'544c9ca7e4b023134754188b','userId':null,'userName':'testkang','hcType':'4','gameCode':'1101','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'01','rateZh':null,'rate':8.12,'rate2':0.0,'betAmount':200.0,'frontAmount':null,'backAmount':null,'mayAmount':1424.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414306983544,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage ':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null,'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false},{'returnResult':0,'entity':null, 'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-458218733,'timeSecond':1414306965,'inc':1196693639,'time':1414306965000,'new':false},'pid':'544c9c95e4b0231347541887','userId':null,'userName':'testkang','hcType':'4','gameCode':'1001','pgameCode':null,'ggameCode':'','issue':'2014148','betBall':'01','rateZh':null,'rate':47.0,'rate2':0.0,'betAmount':100.0,'frontAmount':null,'backAmount':null,'mayAmount':4600.0,'backCommision':0.0,'backCommisionBl':null,'passType':null,'betType':null,'tuoType':null,'status':null,'calcStatus':'0','winResult ':'0','winAmount':null,'awinAmount':null,'isBuo':null,'betTime':1414306965055,'ip':null,'ups':null,'upc':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'iacounted':1.0,'bhcounted':null,'shcounted':null,'gtcounted':null,'agcounted':null,'zkiacounted':null,'fkbhcounted':null,'gkshcounted':null,'zkgtcounted':null,'dkagcounted':null,'fsrevealds':null,'gdrevealds':null,'zdrevealds':null,'counted':null,'revealds':null},'workId':null,'userType':null,'createId':null,'sbdId':null,'outNoteId':null,'srvName':null,'dbType':null,'waiType':null,'dbs':null, 'bncs':null,'calcStr':null,'rateCha':null,'bnNumber':null,'bnns':null,'ccs':null,'deleteWaiDiao':false,'updateWaiDiao':false}],'userHistory':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'id':{'machine':-424603757,'timeSecond':1414306229,'inc':1857953407,'time':1414306229000,'new':false},'pid':'544c99b5e6b10f936ebe1e7f','userId':'544c970ae4b0ce15fb1aa5a0','userName':'testkang','issue':'2014148','billCount':64,'billTotalCount':64,'betAmount':1642.0,'betAmountValid':0.0,'commission':0.0,'rst':829187.7599237061,'awinAmount':null,'dailiName':'', 'specialBonus':null,'deposit':1000000.0,'withdrwaals':null,'winAmount':null,'loassAmount':null,'settleTime':null,'openAwardTime':null}}";
		List<Map<String, Object>> orderResult = new ArrayList<Map<String, Object>>();
		if (response != null && !response.equals("")) {
			try {
				Map<String, String> i18n = (Map<String, String>) request.getAttribute("msg");
				JSONObject jsonObject = JSON.parseObject(response);
				// jsonResult.setCp(jsonObject.getInteger("targetpage"));
				jsonResult.setCp(cp);
				jsonResult.setTps(jsonObject.getInteger("pagecount"));
				JSONObject jsonSum = JSON.parseObject(jsonObject.getString("userHistory"));
				Map<String, Object> sum = new HashMap<String, Object>();
				sum.put("bc", null == jsonSum ? 0 : jsonSum.getIntValue("billCount"));// 有效注数
				sum.put("samt", null == jsonSum ? 0 : jsonSum.getDoubleValue("betAmount"));// 总下注金额
				sum.put("spl", null == jsonSum ? 0 : jsonSum.getDoubleValue("rst"));// 总可赢
				sum.put("com", null == jsonSum ? 0 : jsonSum.getDoubleValue("commission"));// 佣金
				sum.put("twin", null == jsonSum ? 0 : jsonSum.getDoubleValue("awinAmount"));// 总结果
				if (issue != null && !issue.equals("")) {
					sum.put("ob", jsonObject.getString("openBall"));
				}
				jsonResult.setData(sum);
				JSONArray orderArray = JSON.parseArray(jsonObject.get("orderList").toString());
				for (Object obj : orderArray) {
					JSONObject acc = (JSONObject) obj;
					Map<String, Object> order = new HashMap<String, Object>();
					// 小单id
					order.put("id", acc.getString("pid"));
					// 大单id
					// order.put("bid", acc.getString("bnNumber"));
					// 类型
					String gameCode = acc.getString("gameCode");
					if (gameCode.contains("47") || gameCode.contains("46") || gameCode.contains("50")) {
						if (gameCode.length() > 2) {
							order.put("gn", i18n.get("title.qxc.game" + gameCode.substring(0, 2)) + "@" + i18n.get("title.qxc.game" + gameCode));
						} else {
							order.put("gn", i18n.get("title.qxc.game" + gameCode));
						}
					} else if (gameCode.contains("48") || gameCode.contains("49")) {
						order.put("gn", i18n.get("title.qxc.game" + gameCode));
					} else if (gameCode.length() > 2) {
						order.put("gn", i18n.get("title.qxc.game" + gameCode.substring(0, 2)));
					} else {
						order.put("gn", i18n.get("title.qxc.game" + gameCode));
					}
					// 内容显示
					order.put("ball", acc.getString("betBall"));
					// 期号
					order.put("issue", acc.getString("issue"));
					// 盘口Handicap
					order.put("hdcp", QxcHandicap.valueOf(acc.getInteger("hcType")));
					// 赔率odds
					order.put("odsz", acc.getString("rateZh"));
					order.put("ods1", ArithUtil.formatDouble_3(acc.getDouble("rate")));
					order.put("ods2", ArithUtil.formatDouble_3(acc.getDouble("rate2") == null ? 0 : acc.getDouble("rate2")));
					// 下注时间bet time
					order.put("bt", acc.getDate("betTime"));
					// 下注金额
					order.put("amt", ArithUtil.formatDouble(acc.getDoubleValue("betAmount")));
					// 佣金(退水)
					order.put("com", ArithUtil.formatDouble(acc.getDoubleValue("backCommision")));
					// 可赢金额 can money
					order.put("cmny", ArithUtil.formatDouble(acc.getDoubleValue("mayAmount")));
					// 结果,只有历史明细才有
					order.put("win", ArithUtil.formatDouble(acc.getDoubleValue("awinAmount")));
					orderResult.add(order);
				}
				// 只有页面加载的时候才返回玩法列表数据
				if (first) {
					Map<Integer, Object> plays = new TreeMap<Integer, Object>();
					JSONArray playArray = JSON.parseArray(jsonObject.get("playList").toString());
					if (null != playArray) {
						for (Object obj : playArray) {
							JSONObject temp = (JSONObject) obj;
							int gameCode = temp.getInteger("gameCode");
							// 暂时屏蔽五六七定位
							if (gameCode == 41 || gameCode == 42 || gameCode == 43) {
								continue;
							}
							if (gameCode == 52) {
								plays.put(gameCode, i18n.get("title.qxc.game" + gameCode));
							} else {
								plays.put(gameCode, BASE64Util.getBASE64(temp.getString("gameName")));
							}
						}
					}
					sum.put("plays", plays);
				}
				jsonResult.addAll(orderResult);
			} catch (Exception e) {
				jsonResult = JsonResult.createFalied();
				log.error("调用[qxc]手机服务端[orderinfo]接口返回数据格式异常:", e);
			}
		} else {
			jsonResult.setCp(0);
			jsonResult.setTps(0);
			jsonResult.addAll(orderResult);
			Map<String, Object> sum = new HashMap<String, Object>();
			sum.put("plays", null);
			jsonResult.setData(sum);
			log.error("调用[qxc]手机服务端[orderinfo]接口返回数据为空");
		}
		return jsonResult;
	}

	/**
	 * 跳转到开奖结果
	 */
	@At("/gameResult")
	@Ok("jsp:page.qxc.user.gameResult")
	public void toGameResult() {
	}

	@At("/getGameResult")
	@Ok("json")
	public JsonResult getGameResult(int cp) {
		JsonResult jsonResult = JsonResult.createSuccess();
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/kaijiang").toString();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("targetpage", String.valueOf(cp));
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		String response = HttpClientUtil.doGetString(url, parameters);
		// String response =
		// " {'infoList':[{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':17,'id':{'machine':-458220945,'timeSecond':1411466251,'inc':-377715472,'time':1411466251000,'new':false},'pid':'5421440be4b01a6fe97c84f0','issue':'2014147','current':false,'openBall':'02,10,04,05,06,07,11','openAwardTime':1411746003000,'openAwardTimely':1411473094452,'animal':'蛇 鸡 兔 虎 牛 鼠+猴','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1411742190000,'hpStatus':'0','nsealTime':1411745941000,'nseal':'0','seal':'0','sealTime':1411746002000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'45','firstsd':'单','bigSmall':'小','comFirstsd':'合双','endBigSmall':'60','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'0','isc':'1','jieTime':1411466303577},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1413962394080,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':14,'id':{'machine':-458220945,'timeSecond':1411466074,'inc':-377717498,'time':1411466074000,'new':false},'pid':'5421435ae4b01a6fe97c7d06','issue':'2014146','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1411573203000,'openAwardTimely':1411466175207,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1411569390000,'hpStatus':'0','nsealTime':1411573141000,'nseal':'0','seal':'0','sealTime':1411573202000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'0','isc':'1','jieTime':1411466188531},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1411466247303,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':14,'id':{'machine':-458195162,'timeSecond':1410253600,'inc':647756434,'time':1410253600000,'new':false},'pid':'540ec320e4b07f26269bfa92','issue':'2014145','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1411400403000,'openAwardTimely':1410884589585,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1411396590000,'hpStatus':'0','nsealTime':1411400341000,'nseal':'0','seal':'0','sealTime':1411400402000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'0','isc':'1','jieTime':1410884601587},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1411466070556,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':19,'id':{'machine':-458184498,'timeSecond':1408689316,'inc':1587036523,'time':1408689316000,'new':false},'pid':'53f6e4a4e4b0a8ce5e98416b','issue':'2014144','current':false,'openBall':'02,03,04,05,06,07,01','openAwardTime':1411227603000,'openAwardTimely':1410253581692,'animal':'蛇 龙 兔 虎 牛 鼠+马','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1411223790000,'hpStatus':'0','nsealTime':1411227541000,'nseal':'0','seal':'0','sealTime':1411227602000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'60','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1410253591250},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1410253596175,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':20,'id':{'machine':-458206011,'timeSecond':1405609814,'inc':459857030,'time':1405609814000,'new':false},'pid':'53c7e756e4b054c51b68dc86','issue':'2014143','current':false,'openBall':'01,03,04,05,06,07,02','openAwardTime':1411054803000,'openAwardTimely':1408688315524,'animal':'马 龙 兔 虎 牛 鼠+蛇','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1411050990000,'hpStatus':'0','nsealTime':1411054741000,'nseal':'0','seal':'0','sealTime':1411054802000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'双','bigSmall':'小','comFirstsd':'合双','endBigSmall':'60','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1408689297651},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1408689310663,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458214044,'timeSecond':1403700304,'inc':1982622142,'time':1403700304000,'new':false},'pid':'53aac450e4b03564762c69be','issue':'2014142','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1403710803000,'openAwardTimely':1403700353461,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1403706990000,'hpStatus':'0','nsealTime':1403710741000,'nseal':'0','seal':'0','sealTime':1403710802000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1403700368286},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1403700373786,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':23,'id':{'machine':-458206712,'timeSecond':1402824135,'inc':-650262979,'time':1402824135000,'new':false},'pid':'539d65c7e4b05208d93dc63d','issue':'2014141','current':false,'openBall':'12,04,22,42,45,10,08','openAwardTime':1403538003000,'openAwardTimely':1402828324768,'animal':'羊 兔 鸡 牛 狗 鸡+猪','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1403534190000,'hpStatus':'0','nsealTime':1403537941000,'nseal':'0','seal':'0','sealTime':1403538002000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'143','firstsd':'双','bigSmall':'小','comFirstsd':'合双','endBigSmall':'59','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1402828341869},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1403700298760,'newAndNotKai':true,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':17,'id':{'machine':-458206712,'timeSecond':1402815822,'inc':-650265004,'time':1402815822000,'new':false},'pid':'539d454ee4b05208d93dbe54','issue':'2014140','current':false,'openBall':'03,04,05,06,07,08,09','openAwardTime':1403365203000,'openAwardTimely':1402815835109,'animal':'龙 兔 虎 牛 鼠 猪+狗','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1403361390000,'hpStatus':'0','nsealTime':1403365141000,'nseal':'0','seal':'0','sealTime':1403365202000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'42','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':null},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1402824065019,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458206712,'timeSecond':1402815581,'inc':-650267030,'time':1402815581000,'new':false},'pid':'539d445de4b05208d93db66a','issue':'2014139','current':false,'openBall':'05,11,25,36,10,09,06','openAwardTime':1403192403000,'openAwardTimely':1402815622590,'animal':'虎 猴 马 羊 鸡 狗+牛','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1403188590000,'hpStatus':'0','nsealTime':1403192341000,'nseal':'0','seal':'0','sealTime':1403192402000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'102','firstsd':'双','bigSmall':'小','comFirstsd':'合双','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':null},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1402815729758,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':18,'id':{'machine':-458206712,'timeSecond':1402815190,'inc':-650269056,'time':1402815190000,'new':false},'pid':'539d42d6e4b05208d93dae80','issue':'2014138','current':false,'openBall':'10,04,05,45,12,25,13','openAwardTime':1403019603000,'openAwardTimely':1402815522944,'animal':'鸡 兔 虎 狗 羊 马+马','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1403015790000,'hpStatus':'0','nsealTime':1403019541000,'nseal':'0','seal':'0','sealTime':1403019602000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'114','firstsd':'单','bigSmall':'小','comFirstsd':'合双','endBigSmall':'60','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1402815537181},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1402815552125,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':22,'id':{'machine':-458206712,'timeSecond':1402811907,'inc':-650271084,'time':1402811907000,'new':false},'pid':'539d3603e4b05208d93da694','issue':'2014137','current':false,'openBall':'01,02,03,04,05,06,49','openAwardTime':1402846803000,'openAwardTimely':1402812759449,'animal':'马 蛇 龙 兔 虎 牛+马','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1402842990000,'hpStatus':'0','nsealTime':1402846741000,'nseal':'0','seal':'0','sealTime':1402846802000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'70','firstsd':'和','bigSmall':'和','comFirstsd':'和','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1402812776179},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1402812780760,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':16,'id':{'machine':-458199644,'timeSecond':1401287913,'inc':-2141091036,'time':1401287913000,'new':false},'pid':'5385f4e9e4b06da480618b24','issue':'2014134','current':false,'openBall':'22,01,08,49,25,11,33','openAwardTime':1401550803000,'openAwardTimely':1401527822903,'animal':'鸡 马 猪 马 马 猴+狗','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1401546990000,'hpStatus':'0','nsealTime':1401550741000,'nseal':'0','seal':'0','sealTime':1401550802000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'149','firstsd':'单','bigSmall':'大','comFirstsd':'合双','endBigSmall':'60','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1401527831316},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1401528007010,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':14,'id':{'machine':-458172441,'timeSecond':1401176724,'inc':-1502643826,'time':1401176724000,'new':false},'pid':'53844294e4b0d7e7a66f798e','issue':'2014133','current':false,'openBall':'02,03,04,05,06,07,08','openAwardTime':1407858003000,'openAwardTimely':1401176758479,'animal':'蛇 龙 兔 虎 牛 鼠+猪','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1407854190000,'hpStatus':'0','nsealTime':1407857941000,'nseal':'0','seal':'0','sealTime':1407858002000,'isAuto':'1'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'35','firstsd':'双','bigSmall':'小','comFirstsd':'合双','endBigSmall':'59','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1401176771018},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1401176858089,'newAndNotKai':true,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':17,'id':{'machine':-458210780,'timeSecond':1400569380,'inc':-1456579415,'time':1400569380000,'new':false},'pid':'537afe24e4b04224a92e5ca9','issue':'2014132','current':false,'openBall':'09,10,11,12,13,14,08','openAwardTime':1407685203000,'openAwardTimely':1401176683029,'animal':'狗 鸡 猴 羊 马 蛇+猪','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1407681390000,'hpStatus':'0','nsealTime':1407685141000,'nseal':'0','seal':'0','sealTime':1407685202000,'isAuto':'1'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'77','firstsd':'双','bigSmall':'小','comFirstsd':'合双','endBigSmall':'59','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1401176695975},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1401176700597,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458210690,'timeSecond':1399542609,'inc':1143707780,'time':1399542609000,'new':false},'pid':'536b5351e4b0427e442b9884','issue':'2014131','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1407512403000,'openAwardTimely':1399542692140,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1407508590000,'hpStatus':'0','nsealTime':1407512341000,'nseal':'0','seal':'0','sealTime':1407512402000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399542701351},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399542706123,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458210690,'timeSecond':1399541721,'inc':1143705733,'time':1399541721000,'new':false},'pid':'536b4fd9e4b0427e442b9085','issue':'2014130','current':false,'openBall':'10,04,05,06,07,08,09','openAwardTime':1407339603000,'openAwardTimely':1399541792794,'animal':'鸡 兔 虎 牛 鼠 猪+狗','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1407335790000,'hpStatus':'0','nsealTime':1407339541000,'nseal':'0','seal':'0','sealTime':1407339602000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'49','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总单','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399541811025},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399541817096,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458210690,'timeSecond':1399541330,'inc':1143703646,'time':1399541330000,'new':false},'pid':'536b4e52e4b0427e442b885e','issue':'2014129','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1407166803000,'openAwardTimely':1399541359207,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1407162990000,'hpStatus':'0','nsealTime':1407166741000,'nseal':'0','seal':'0','sealTime':1407166802000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399541410840},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399541415460,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':15,'id':{'machine':-458210690,'timeSecond':1399541280,'inc':1143701619,'time':1399541280000,'new':false},'pid':'536b4e20e4b0427e442b8073','issue':'2014128','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1406994003000,'openAwardTimely':1399541305858,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1406990190000,'hpStatus':'0','nsealTime':1406993941000,'nseal':'0','seal':'0','sealTime':1406994002000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399541320799},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399541324056,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':33,'id':{'machine':-458166465,'timeSecond':1399539440,'inc':2096422979,'time':1399539440000,'new':false},'pid':'536b46f0e4b0ef3f7cf4e043','issue':'2014127','current':false,'openBall':'01,02,03,04,06,07,05','openAwardTime':1406821203000,'openAwardTimely':1399540431907,'animal':'马 蛇 龙 兔 牛 鼠+虎','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1406817390000,'hpStatus':'0','nsealTime':1406821141000,'nseal':'0','seal':'0','sealTime':1406821202000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399540452468},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399540476241,'newAndNotKai':false,'accounting':false},{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':23,'id':{'machine':-458178242,'timeSecond':1399537988,'inc':-1066364400,'time':1399537988000,'new':false},'pid':'536b4144e4b0c13ec0709210','issue':'2014126','current':false,'openBall':'01,02,03,04,05,06,07','openAwardTime':1406648403000,'openAwardTimely':1399538466859,'animal':'马 蛇 龙 兔 虎 牛+鼠','currentAnimal':'71','muserId':null,'modifier':null,'updateTime':null,'ip':null,'hcm':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'openHadTime':1406644590000,'hpStatus':'0','nsealTime':1406648341000,'nseal':'0','seal':'0','sealTime':1406648402000,'isAuto':'0'},'ai':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'ballSum':'28','firstsd':'单','bigSmall':'小','comFirstsd':'合单','endBigSmall':'59','totalFirstsd':'总双','totalBigSmall':'总小'},'ah':{'returnResult':0,'entity':null,'targetpage':1,'pagesize':20,'orderinfo':'','version':null,'isOpenAward':'1','isAccount':'1','status':'1','isc':'1','jieTime':1399539078606},'mc':null,'mc1':null,'isChange':0,'mand':null,'proxyCache':'0','calcMethod':null,'queryMode':null,'cashResult':1,'calculateOverTime':1399539101473,'newAndNotKai':false,'accounting':false}]}";
		if (response != null && !response.equals("")) {
			try {
				JSONObject jsonObject = JSON.parseObject(response);
				JSONArray jsonArray = JSON.parseArray(jsonObject.get("infoList").toString());
				List<Map<String, Object>> accResults = new ArrayList<Map<String, Object>>();
				for (Object obj : jsonArray) {
					JSONObject jo = (JSONObject) obj;
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("issue", jo.getString("issue"));// 2014147
					result.put("date", DateUtil.formatDate(jo.getDate("openAwardTime")));// 时间
					result.put("ball", jo.getString("openBall"));// 02,10,04,05,06,07,11
					JSONObject other = JSON.parseObject(jo.getString("ai"));
					if (other != null) {
						result.put("sum", other.getInteger("ballSum"));// 总和
						// 单双even and odd
						result.put("eo", other.getString("firstsd"));
						result.put("bs", other.getString("bigSmall"));// 大小
						result.put("ceo", other.getString("comFirstsd"));// 合单双composite
						result.put("seo", other.getString("totalFirstsd"));// 总和单双
						result.put("sbs", other.getString("totalBigSmall"));// 总和大小
					}
					accResults.add(result);
				}
				jsonResult.addAll(accResults);
			} catch (Exception e) {
				jsonResult = JsonResult.createFalied();
				log.error("调用[qxc]手机服务器端[kaijiang]接口返回数据格式异常,response << " + response, e);
			}
		} else {
			jsonResult = JsonResult.createFalied();
			log.error("调用[qxc]手机服务器端[kaijiang]接口返回数据为空,response << " + response);
		}
		return jsonResult;
	}

	/**
	 * 游戏规则
	 */
	@At("/gameRules")
	@Ok("jsp:page.qxc.user.gameRules")
	public void gameRules() {
	}

	/**
	 * 跳转到公告栏
	 */
	@At("/afficheInfo")
	@Ok("jsp:page.qxc.user.afficheInfo")
	public void toAfficheInfo() {

	}

	/**
	 * 获取公告栏数据
	 * 
	 * @return
	 */
	@At("/getAfficheInfo")
	@Ok("json")
	public JsonResult getAfficheInfo(HttpSession session) {
		JsonResult jsonResult = JsonResult.createSuccess();
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/memberGg").toString();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("targetpage", "1");
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		String response = HttpClientUtil.doGetString(url, parameters);
		if (response != null && !response.equals("")) {
			try {
				JSONObject jsonObject = JSON.parseObject(response);
				JSONArray jsonArray = JSON.parseArray(jsonObject.get("info").toString());
				List<Map<String, Object>> accResults = new ArrayList<Map<String, Object>>();
				for (Object obj : jsonArray) {
					JSONObject jo = (JSONObject) obj;
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("d", jo.getDate("createTime"));
					result.put("m", BASE64Util.getBASE64(jo.getString("message")));
					accResults.add(result);
				}
				jsonResult.addAll(accResults);
			} catch (Exception e) {
				jsonResult = JsonResult.createFalied();
				log.error("调用[qxc]手机服务器端[memberGg]接口返回数据格式异常,response << " + response, e);
			}
		} else {
			jsonResult = JsonResult.createFalied();
			log.error("调用[qxc]手机服务器端[memberGg]接口返回数据为空,response << " + response);
		}
		return jsonResult;
	}

	/**
	 * 跳转到用户基本信息页面
	 */
	@At("/userInfo")
	@Ok("jsp:page.qxc.user.userInfo")
	public void toUserInfo() {
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param rtype
	 * 
	 * @return rtype为0返回用户基本信息+限额信息，rtype为1只返回基本信息
	 */
	/**
	 * 获取用户基本信息
	 * 
	 * @return
	 */
	@At("/getUserInfo")
	@Ok("json")
	public JsonResult getUserInfo(int rtype, HttpSession session, HttpServletRequest request) {
		JsonResult jsonResult = JsonResult.createSuccess();
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/memberinfo").toString();
		QxcUser qxcUser = (QxcUser) session.getAttribute(com.vinbet.mobile.util.Constants.QXC_USER_SESSION);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", qxcUser.getAccoutName());
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		JSONObject jsonObject = HttpClientUtil.doGet(url, parameters);
		if (jsonObject != null && jsonObject.size() != 0) {
			try {
				String code = jsonObject.getString("code");
				if ("10000".equals(code)) {
					Map<String, Object> user = new HashMap<String, Object>();
					JSONObject userAccount = JSON.parseObject(jsonObject.get("member").toString());
					user.put("sc", userAccount.getDoubleValue("credits"));// 信用额度
					user.put("ac", userAccount.getDoubleValue("avacredits"));// 信用余额
					qxcUser.setBalance(userAccount.getDoubleValue("avacredits"));
					user.put("an", userAccount.getString("name"));// 账号
					user.put("nm", userAccount.getString("username"));// 会员名称
					user.put("tp", userAccount.getString("accountType"));// 用户类型:1现金、0信用
					int status = userAccount.getIntValue("status");
					int accountType = userAccount.getIntValue("accountType");
					user.put("sts", status);// 状态: 1 正常、 2 停用
					user.put("tp", accountType);
					// qxcUser.setHandype(userAccount.getString("handype"));
					user.put("htn", qxcUser.getHandypeText());
					jsonResult.setData(user);
					qxcUser.setCredits(userAccount.getDoubleValue("credits"));
					qxcUser.setBalance(userAccount.getDoubleValue("avacredits"));
					if (rtype == 0) {
						JSONObject comms = null;
						try {
							Map<String, String> i18n = (Map<String, String>) request.getAttribute("msg");
							String curl = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/comminfo").toString();
							Map<String, String> prams = new HashMap<String, String>();
							prams.put("username", qxcUser.getAccoutName());
							prams.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
							comms = HttpClientUtil.doGet(curl, prams);
							JSONArray clist = new JSONArray();
							if (comms != null && comms.getString("comm") != null) {
								JSONObject object = JSONObject.parseObject(comms.getString("comm"));
								String[] keys = object.keySet().toArray(new String[0]);
								Arrays.sort(keys);
								for (String key : keys) {
									String gameCtx = AppCache.qxcSysConfig.get("gc_" + key);// modify by jagger 20160401
									if (gameCtx == null || gameCtx.equals("")) {
										continue;
									}
									JSONObject last = new JSONObject();
									JSONObject value = object.getJSONObject(key);
									last.put("gn", i18n.get("title.qxc.game" + key));
									last.put("c", value.get("comm"));
									last.put("cs", value.get("commSel"));
									last.put("gc", value.get("gameCode"));
									last.put("rmx", value.get("rateMax"));
									last.put("rs", value.get("rateSel"));
									last.put("stm", value.get("singTermMax"));
									last.put("smx", value.get("singeMax"));
									last.put("smi", value.get("singeMin"));
									clist.add(last);
								}
							}
							jsonResult.addAll(clist);
						} catch (Exception e) {
							jsonResult.addAll(new JSONArray());
							log.error("获取用户交易回水信息,调用七星彩手机服务端[comminfo]接口返回数据格式异常,response >> " + (null == comms ? null : comms.toJSONString()), e);
						}

						/**
						 * // 处理限额信息 JSONArray upy = JSONArray.parseArray(userAccount.getString("upy")); JSONArray list = new JSONArray(); for (Object
						 * object : upy) { JSONObject orgin = (JSONObject) object; String gameCode = orgin.getString("gameCode"); // 暂时屏蔽五六七定位 if
						 * ("41,42,43".contains(gameCode)) { continue; } JSONObject last = new JSONObject(); last.put("p",
						 * BASE64Util.getBASE64(orgin.getString("playname")));// 玩法名称 last.put("l", orgin.getString("singlelowest"));// 单注最低
						 * last.put("h", orgin.getString("singlehighest"));// 单注最高 last.put("x", orgin.getString("danxhighest"));// 单项最高 StringBuilder
						 * handtypeComn = new StringBuilder(); if (qxcUser.getHandype().contains("1")) { handtypeComn.append("A: " +
						 * orgin.getString("acommission"));
						 * 
						 * } if (qxcUser.getHandype().contains("2")) { handtypeComn.append(" B: " + orgin.getString("bcommission"));
						 * 
						 * } if (qxcUser.getHandype().contains("3")) { handtypeComn.append(" C: " + orgin.getString("ccommission"));
						 * 
						 * } if (qxcUser.getHandype().contains("4")) { handtypeComn.append(" D: " + orgin.getString("dcommission"));
						 * 
						 * } last.put("m", handtypeComn.toString());// 盘口佣金 list.add(last); } jsonResult.addAll(list);
						 */
					}
				} else {
					jsonResult = JsonResult.createFalied();
					log.error("获取用户基本信息，调用七星彩手机服务端[memberinfo]接口返回失败，response >>" + jsonObject.toJSONString());
				}
			} catch (Exception e) {
				jsonResult = JsonResult.createFalied();
				log.error("获取用户基本信息,调用七星彩手机服务端[memberinfo]接口返回数据格式异常,response >> " + (null == jsonObject ? null : jsonObject.toJSONString()), e);
			}
		} else {
			jsonResult = JsonResult.createFalied();
			log.error("获取用户基本信息,调用七星彩手机服务端[memberinfo]接口返回数据为空,response >> " + (null == jsonObject ? null : jsonObject.toJSONString()));
		}
		return jsonResult;

	}

	/**
	 * 跳转到账户历史按期汇总
	 */
	@At("/accountHistory")
	@Ok("jsp:page.qxc.user.accountHistory")
	public void toAccoutHistory() {
	}

	/**
	 * 获取账户历史按期汇总
	 * 
	 * @param orderType
	 * @return
	 */
	@At("/getAccoutHistory")
	@Ok("json")
	public JsonResult getAccoutHistory(HttpSession session) {
		JsonResult jsonResult = JsonResult.createSuccess();
		QxcUser user = (QxcUser) session.getAttribute(com.vinbet.mobile.util.Constants.QXC_USER_SESSION);
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/historyinfo").toString();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", user.getAccoutName());
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		String response = HttpClientUtil.doGetString(url, parameters);
		// String response =
		// "{'code':'10000','description':'success','historyList':[{'openAwardTime':23423323423,'issue':2014129,'billCount':12,'betAmount':3000,'commission':0,'awinAmount':90}{'openAwardTime':23423323423,'issue':2014128,'billCount':12,'betAmount':3000,'commission':0,'awinAmount':90}],'totalBillCount':2,'totalBetAmount':2.0,'totalCommission':0.0,'totalResult':2.0}";
		if (response != null && !response.equals("")) {
			try {
				JSONObject jsonObject = JSON.parseObject(response);
				// 汇总数据
				Map<String, Object> sum = new HashMap<String, Object>();
				sum.put("tbc", jsonObject.getIntValue("totalBillCount"));// 有效注数
				sum.put("tba", jsonObject.getDoubleValue("totalBetAmount"));// 总下注金额
				sum.put("tr", jsonObject.getDoubleValue("totalResult"));// 总可赢
				sum.put("tc", jsonObject.getDoubleValue("totalCommission"));// 佣金
				jsonResult.setData(sum);
				// 详情
				JSONArray jsonArray = JSON.parseArray(jsonObject.get("historyList").toString());
				List<Map<String, Object>> accResults = new ArrayList<Map<String, Object>>();
				if (jsonArray != null) {
					for (Object obj : jsonArray) {
						JSONObject acc = (JSONObject) obj;
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("bc", acc.getIntValue("billTotalCount"));// 注数
						result.put("ba", acc.getDouble("betAmount"));// 下注金额
						result.put("com", acc.getDouble("commission"));// 佣金
						result.put("win", acc.getDouble("awinAmount"));// 结果
						result.put("date", acc.getDate("openAwardTime"));// 日期
						result.put("issue", acc.getString("issue"));// 期数
						accResults.add(result);
					}
				}
				jsonResult.addAll(accResults);
			} catch (Exception e) {
				jsonResult = JsonResult.createFalied();
				log.error("调用[qxc]手机服务器端[historyinfo]接口返回数据格式异常", e);
			}
		} else {
			jsonResult = JsonResult.createFalied();
			log.error("调用[qxc]手机服务器端[historyinfo]接口返回数据为空");
		}
		return jsonResult;
	}

	/**
	 * 回水设置
	 * 
	 * @param param
	 * @param session
	 * @return
	 */
	@At("/setcomm")
	@Ok("json")
	public JsonResult setcomm(String param, HttpSession session) {
		JsonResult jsonResult = JsonResult.createSuccess();
		QxcUser user = (QxcUser) session.getAttribute(com.vinbet.mobile.util.Constants.QXC_USER_SESSION);
		String url = new StringBuilder(propertiesLoader.getTrim("vinbet.qxc.url")).append("/setcomm").toString();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getAccoutName());
		parameters.put("parm", param);
		parameters.put(Constants.TOKEN_HKC, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.qxc")));
		try {
			JSONObject jo = HttpClientUtil.doPost(url, parameters);
			if (jo != null && !jo.equals("")) {
				jsonResult.setData(jo);
			} else {
				jsonResult = JsonResult.createFalied();
			}
		} catch (Exception e) {
			jsonResult = JsonResult.createFalied();
			log.error("调用七星彩手机服务器端[setcomm]接口异常>> ", e);
		}

		return jsonResult;
	}
}
