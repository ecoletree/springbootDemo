/*
Version : 0.0.8
2021.11.13 : 로그아웃 시 mqtt unsubscribe (unsub_topics 함수) 추가
*/

var connected_flag=0	
var mqtt;
var reconnectTimeout = 2000;
var host="117.52.89.240";
var port=9001;
var ApiKey = 'Unknown';
var Agent_id = 0;
var Agent_phone = 0;

var Tenant = 0;
var Skill = 0;
var Group = 0;
var Team = 0;
var phone_ip = "";
var event_callback_fp, system_callback_fp;

function do_login(tenant, skill, agent, pw, phone){
    /*
    var agent= document.forms["connform"]["agentid"].value;
    var pw= document.forms["connform"]["agentpw"].value;
    var phone= document.forms["connform"]["phone"].value;
    */
    const topic_str = 'master/agent'
    Agent_id = agent;
    Agent_phone = phone;
    Tenant = tenant;
    Skill = skill;
    //sub_topics();
	sub_agent_topics();

    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = agent;
    event["Password"] = pw;
    event["Phone"] = phone;
    event["Command"] = 'Login';
    event["Seq"] = 100;
    jsonObj = JSON.stringify(event);
    //console.log(ApiKey);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_logout(){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Command"] = 'Logout';
    event["Seq"] = 101;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}

//function do_break(){
//    //var agent= document.forms["connform"]["agentid"].value;
//    const topic_str = 'master/agent'
//    var event = new Object() ;
//    event["Event-Name"] = 'Agent';
//    event["Id"] = Agent_id;
//    event["Command"] = 'OnBreak';
//    event["Seq"] = 102;
//    event["ApiKey"] = ApiKey;
//    jsonObj = JSON.stringify(event);
//    console.log(jsonObj);
//    send_message(topic_str, jsonObj);
//}

function do_break(break_status){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Command"] = 'OnBreak';
    event["Substate"] = break_status;
    event["Seq"] = 102;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}

function do_available(){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Command"] = 'Available';
    event["Seq"] = 103;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_acw(){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Command"] = 'Acw';
    event["Seq"] = 104;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_autoacw(){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Command"] = 'AutoAcw';
    event["Seq"] = 105;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_recordstart(announce_record, record_key){

    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Phone"] = Agent_phone;
    event["Command"] = 'RecordStart';
    if (announce_record === true)
        event["AnnounceKey"] = "1";
    else{
        event["AnnounceKey"] = "0";
    }	
    event["Seq"] = 106;
    event["ApiKey"] = ApiKey;
    event["RecordKey"] = record_key;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_recordstop(){
    //var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Id"] = Agent_id;
    event["Phone"] = Agent_phone;
    event["Command"] = 'RecordStop';
    event["Seq"] = 107;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}
function do_playment(ment){
    // var ment= document.forms["connform"]["mentfilename"].value;
    // var agent= document.forms["connform"]["agentid"].value;

    const topic_str = 'master/agent'
    var playment = new Object() ;
    playment["Event-Name"] = 'Agent';
    playment["Id"] = Agent_id;
    playment["Phone"] = Agent_phone;
    playment["Command"] = 'PlayMent';
    playment["MentFile"] = ment;
    playment["Seq"] = 108;
    playment["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(playment);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}


/*
전화기 API는 직접 전화기와 통신
*/
function phone_api(phoneip, param){
    //var phoneip= document.forms["connform"]["phoneip"].value;
/*
    var rest_api = "http://" + phoneip +  "/cgi-bin/ConfigManApp.com?key=";
    var comamnd = rest_api + param;
	if (phoneip.indexOf('127.0.0.1') != -1)	//for softphone -> use %3B instead of ;
		comamnd = rest_api + encodeURIComponent(param);
	
    console.log("phone uri command:" + comamnd);

    var iframe = $('<iframe src="'+comamnd+'" style="display:none;"></iframe>');
    $('body').append(iframe);
        iframe.load(function(){
                iframe.remove();
        });
*/

    const topic_str = 'master/agent'
    var active_uri = new Object() ;
    active_uri["Event-Name"] = 'Agent';
    active_uri["Id"] = Agent_id;
    active_uri["Phone"] = Agent_phone;
    active_uri["Command"] = 'PhoneActiveUri';
    active_uri["Uri"] = param;
    active_uri["Type"] = ""; //phone_ip;	// login시 받은 phone ip -> cti에서 체크
    active_uri["Seq"] = 201;
    active_uri["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(active_uri);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);

}
function do_speaker_phone_makecall(phoneip, target){
    //var target = document.getElementById('phonenum').value;
    var param = "SPEAKER";
    phone_api(phoneip, param);
    setTimeout(() => {
        param = target + ";" + "ENTER";
        phone_api(phoneip, param);
        
    }, 1000);
}
function do_headset_phone_makecall(phoneip, target){
    //var target = document.getElementById('phonenum').value;
    // var param = "HEADSET;" + target + ";ENTER";
    var param = "HEADSET";
    phone_api(phoneip, param);
    setTimeout(() => {
        param = target + ";" + "ENTER";
        phone_api(phoneip, param);
        
    }, 1000);
}
function do_phone_answer(phoneip){
    var param = "OK";
    return phone_api(phoneip, param);
}
function do_phone_mic_mute(phoneip){
    var param = "F_MUTE";
    return phone_api(phoneip, param);
}

function do_phone_release(phoneip){
    var param = "RELEASE";
    return phone_api(phoneip, param);
}
function do_phone_reject(phoneip){
    var param = "F_REJECT";
    return phone_api(phoneip, param);
}

function do_phone_hold(phoneip){
    var param = "F_HOLD";
    return phone_api(phoneip, param);
}
function do_phone_unhold(phoneip){  //release hld
    var param = "F_HOLD";
    return phone_api(phoneip, param);
}

function do_phone_attended_start(phoneip, target){
    //var target = document.getElementById('phonenum').value;
    var param = "F_TRANSFER" + ";" + target + ";" + "OK";
    return phone_api(phoneip, param);
}
function do_phone_attended_complete(phoneip, target){
    var param = "F_TRANSFER";
    return phone_api(phoneip, param);
//    return do_phone_attended_start(phoneip, target);
}

function do_phone_blind_complete(phoneip, target){
    var param = "F_TRANSFER" + ";" + target + ";" + "F_A_TRANSFER";
    return phone_api(phoneip, param);
//    return do_phone_attended_start(phoneip, target);
}

function do_ivr_3way_prepare(phoneip, target){
    //var target = document.getElementById('phonenum').value;

    var param = "F_CONFERENCE";
    phone_api(phoneip, param);
    setTimeout(() => {
        param = target + ";" + "ENTER";
        phone_api(phoneip, param);
    }, 500);
}




// 3자 통화를 시작하고 상담원은 MUTE 상태로 변경. IVR 통화 종료 후 HOLD 해제해야 함.
// Hold시 보류 음악이 플레이되는 것을 막기위해 F_HOLD보다 DisableHoldMusic 먼저 보냄.
function do_ivr_3way_start(){
    /*
    var agent= document.forms["connform"]["agentid"].value;
    const topic_str = 'master/agent'
    var playment = new Object() ;
    playment["Event-Name"] = 'Agent';
    playment["Id"] = agent;
    playment["Command"] = 'DisableHoldMusic';
    playment["Seq"] = 109;
    playment["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(playment);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
    */
 
}

function do_ivr_3way_start(){
}

/*
상대방 상담원 또는 자신에게(other값이 agent 본인 또는 "") 메시지 전달
녹취용 데이터를 전달할 경우 자신에게 전달하면 된다. 
*/
function do_pwconfirm(msg, other = ""){
    const topic_str = 'master/agent'

    if(other === "") {
        other = Agent_id;
    }

    var event = new Object() ;
    event["Event-Name"] = 'Agent';
    event["Command"] = 'PwConfirm';
    event["Id"] = Agent_id;
    event["Phone"] = Agent_phone;
    event["Target"] = other;
    event["Msg"] = msg;
    event["ApiKey"] = ApiKey;
    event["Seq"] = 109;

    jsonStr = JSON.stringify(event);
    console.log(jsonStr);
    send_message(topic_str, jsonStr);

}

function do_CTIdisconnect()
{
    if (connected_flag==1)
        mqtt.disconnect();
}
/*
elementId는 메시지를 표시할 엘리먼트이며 생략가능하지만 개발 단계에서는 사용하길 추천한다.
*/
function do_CTIconnect(user_name, password, s, p, event_callback, system_callback) {
    if(connected_flag == 1){
        alert("Already Connectied =>Disconnect first");
    }
    event_callback_fp = event_callback;
    system_callback_fp = system_callback;   
    var clean_sessions= true; 

   var port;
   var host;
    if (p!="")
    {
        port=parseInt(p);
    }
    if (s!="")
    {
        host=s;
        console.log("host");
    }
    
    console.log("connecting to "+ host +" "+ port +" clean session="+clean_sessions);
    console.log("user "+user_name);
    var x=Math.floor(Math.random() * 10000); 
    var cname="orderform-"+x;
    mqtt = new Paho.MQTT.Client(host,port,cname);
    //document.write("connecting to "+ host);
    var options = {
		//useSSL: true,
        timeout: 3,
        cleanSession: clean_sessions,
        onSuccess: onConnect,
        onFailure: onFailure,
    };
    if (user_name !="")
        options.userName=user_name;
    if (password !="")
        options.password=password;
    
    mqtt.onConnectionLost = onConnectionLost;
    mqtt.onMessageArrived = onMessageArrived;
    mqtt.onConnected = onConnected;
    mqtt.connect(options);
    return false;
}

function do_sub_chat(is_agent, other, msg){

    const topic_str = 'master/agent'
    if(is_agent === true) {
        other = "agent/" + other;
    }
    else{
        other = "admin/" + other;
    }


    var event = new Object() ;
    event["Event-Name"] = 'Chat';
    event["Tenant"] = Tenant;
    event["Id"] = Agent_id;
    event["Target"] = other;
    event["Msg"] = msg;
    event["ApiKey"] = ApiKey;
    jsonObj = JSON.stringify(event);
    console.log(jsonObj);
    send_message(topic_str, jsonObj);
}	

function sub_sys_topics(){

    var soptions={
        qos:0,
    };
    stopic = 'master/system'
    console.log("Subscribing to topic ="+stopic );		//시스템 메시지
    mqtt.subscribe(stopic,soptions);

    return false;
}

function sub_agent_topics(){

    var soptions={
        qos:0,
    };

    stopic = 'agent/' + Agent_id;	//나(상담원)의 메시지
    console.log("Subscribing to topic ="+stopic );
    mqtt.subscribe(stopic,soptions);

    return false;
}

function sub_topics(){

    // var tenant= document.forms["connform"]["tenant"].value;
    // var skill= document.forms["connform"]["skill"].value;
    // var agent= document.forms["connform"]["agentid"].value;
    // var phone= document.forms["connform"]["phone"].value;

    var soptions={
        qos:0,
    };

    stopic = 'tenant/' + Tenant + '/' + Skill + '/' + Agent_id;	//나(상담원)의 메시지
    console.log("Subscribing to topic ="+stopic );
    mqtt.subscribe(stopic,soptions);

    stopic = 'tenant/' + Tenant	
    console.log("Subscribing to topic ="+stopic );		//회사 메시지
    mqtt.subscribe(stopic,soptions);

    stopic = 'tenant/' + Tenant + '/station/' + Agent_phone	//전화기 메시지
    console.log("Subscribing to topic ="+stopic );
    mqtt.subscribe(stopic,soptions);

    stopic = 'tenant/' + Tenant + '/' + Skill		//스킬그룹 메시지
    console.log("Subscribing to topic ="+stopic );
    mqtt.subscribe(stopic,soptions);

    return false;
}

function unsub_topics(){
    stopic = 'agent/' + Agent_id;	//나(상담원)의 메시지
    console.log("UnSubscribing to topic ="+stopic );
    mqtt.unsubscribe(stopic);

    stopic = 'tenant/' + Tenant + '/' + Skill + '/' + Agent_id;	//나(상담원)의 메시지
    console.log("UnSubscribing to topic ="+stopic );
    mqtt.unsubscribe(stopic);

    stopic = 'tenant/' + Tenant	
    console.log("UnSubscribing to topic ="+stopic );		//회사 메시지
    mqtt.unsubscribe(stopic);

    stopic = 'tenant/' + Tenant + '/station/' + Agent_phone	//전화기 메시지
    console.log("UnSubscribing to topic ="+stopic );
    mqtt.unsubscribe(stopic);

    stopic = 'tenant/' + Tenant + '/' + Skill		//스킬그룹 메시지
    console.log("UnSubscribing to topic ="+stopic );
    mqtt.unsubscribe(stopic);
    return false;

}
function send_message(topic, msg){
    if (connected_flag==0){
        const out_msg="Not Connected so can't send"
        console.log(out_msg);
        system_callback_fp(4100, out_msg);
        return false;
    }

    const pqos=0;
    message = new Paho.MQTT.Message(msg);
    message.destinationName = topic;
    message.qos=pqos;
    //message.retained=retain_flag;
    mqtt.send(message);
    return false;
}

//아래의 함수들은 이벤트 콜백 함수들임. 실제 구현시 수정 필요함.
function onConnectionLost(){
    console.log("connection lost");
    system_callback_fp(4200, "Connection Lost");
    connected_flag=0;
}
function onFailure(message) {
    console.log("Failed");
    system_callback_fp(4201, "Connection Failed- Retrying");

}
function onMessageArrived(r_message){
    let msg = JSON.parse(r_message.payloadString);
    if (msg["Event-Name"] == "Agent"){
		console.log("Event Seq/Code : " + msg["Seq"] + "/" + msg["Code"]);
		if(msg["Seq"] == 100 && msg["Code"] == "OK"){	// login 성공 시
			Tenant = msg["Tenant"];
			Skill = msg["Skill"];
			Group = msg["GroupId"];
			Team = msg["TeamId"];
			phone_ip = msg["PhoneIp"];			
			console.log("Login OK : " + Group + "/" + Tenant + "/" + Skill + "/" + Team + "/" + Agent_id + " : " + phone_ip);
			sub_topics();
		}
        if(msg.hasOwnProperty("ApiKey") === true){
            console.log("Login 응답에서 API Key를 받음 => 앞으로 보내는 명령에서 사용");
            ApiKey = msg["ApiKey"];
        }
    }
    console.log("RCV:\n" + JSON.stringify(msg));
    event_callback_fp(r_message);


    if (msg["Event-Name"] == "AgentEvent"){
        if(msg["Status"] == "LoggedOut"){       //로그아웃 이벤트 수신
            unsub_topics(); //이벤트 수신 중단
        }
    }

}
    
function onConnected(recon,url){
    console.log(" in onConnected " +reconn);
}
//서버 접속 성공 ->subscribe, 로그인 정보 송신(publish)
function onConnect() {
  // Once a connection has been made, make a subscription and send a message.
    connected_flag=1;
    system_callback_fp(2000, "Connected to "+host +" on port "+ port);
    console.log("on Connect "+ connected_flag);
    sub_sys_topics();
}
