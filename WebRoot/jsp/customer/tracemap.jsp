<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <title></title>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v0.38.0/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.38.0/mapbox-gl.css' rel='stylesheet' />
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }
    </style>
</head>
<body>
 <div id='map'>
 	<FORM id="customerForm" name="customerForm"
		action="${pageContext.request.contextPath }/customer_traceMap.action"
		method=post>
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>MAC地址：</TD>
													<TD><INPUT class=textbox id=sChannel2
														style="WIDTH: 80px" maxLength=50 name="muMac"></TD>
													
													<TD><INPUT class=button id=sButton2 type=submit
														value="查询轨迹信息" name=sButton2></TD>
												</TR>
											</TBODY>
										</TABLE>
										</FORM>
 </div>
<script>
mapboxgl.accessToken = 'pk.eyJ1IjoiamluZy1zYW0iLCJhIjoiY2l6ZXgxcDA3MDAzbjJ3b3d5c3V0cTdxMSJ9.lncV85QVu9XzKlsOzUf9TA';
var styleLocation='${pageContext.request.contextPath }/json/style.json';
var map = new mapboxgl.Map({
    container: 'map', // container id
    style: styleLocation
});
/* var geojson="${pageContext.request.contextPath }/geojson/earthquakes2.geojson"; */
// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl());

map.on('load', function () {
    map.addLayer({
        id: "route",
        type: "line",
        source: {
            "type": "geojson",
            "data":"${pageContext.request.contextPath}/geojson/tracemap.geojson"
            /* {"type": "Feature","geometry": {"type": "LineString","coordinates":[[126.4896550513974,45.87066148370323],[126.4896528791891,45.87064956168927],[126.4896866762301,45.87074342305391],[126.4896932438612,45.87073978015285],[126.4896979511665,45.87074554526577],[126.4897201107977,45.87073442552746],[126.4897043654392,45.87073910100774],[126.4897309813411,45.87073465129785],[126.4897391410162,45.87073561148285],[126.4897485813792,45.87073502974063],[126.4897573748239,45.87073445069905],[126.4897586392687,45.87073101071334],[126.4897591351463,45.87072847780691],[126.489751388373,45.87073040828281],[126.4897525249813,45.87072714960494],[126.4897478942424,45.87073033248649],[126.489739347964,45.87072955469042],[126.4897355749574,45.8707271299965],[126.4897227938241,45.8707304372969],[126.489690142521,45.8707402450367],[126.4897203662445,45.8707491575401],[126.4897117137814,45.87075109179501],[126.4897057515372,45.87073475664477],[126.4897099552296,45.8707421508258],[126.4896877842289,45.87073682017754],[126.4896901316942,45.87073897966407],[126.4897135632769,45.87074041840781],[126.489724042577,45.87074028426451],[126.4897388333014,45.87074501301467],[126.4897531384231,45.87073835503003],[126.4897644793204,45.87073306522677],[126.489759332037,45.87072124602555],[126.4897530814061,45.87071657200365],[126.4897388919739,45.87070650791211],[126.4897246822126,45.87070918846929],[126.489704856368,45.87070574615878],[126.4897037027432,45.87070701639342],[126.4896914244532,45.87070860423795],[126.4896669498416,45.87072136060176],[126.489660562632,45.87073096828935],[126.4896668341361,45.87073808267788],[126.4896795044508,45.87073694513418],[126.4896750443156,45.87072982318369],[126.4896722769945,45.87072395958322],[126.4896722973406,45.87071121493273],[126.4897503059054,45.87073999382383],[126.4897600671291,45.87073154707273],[126.4897566103412,45.87072071506901],[126.4897342061001,45.87071836817437],[126.4897237407254,45.87072012922626],[126.4896801910464,45.87072645737676],[126.4896721940098,45.87072938314906],[126.4896672387677,45.87072488448806],[126.4896550513974,45.87066148370323],[126.4896528791891,45.87064956168927],[126.4897694969079,45.87054829326425],[126.4897694969079,45.87054829326425],[126.4897694969079,45.87054829326425],[126.4897694969079,45.87054829326425],[126.4897650598931,45.87060435172789],[126.4897686671563,45.87060252892441],[126.4897816296753,45.87059018216218],[126.4897816296753,45.87059018216218],[126.4897816296753,45.87059018216218],[126.4897758027872,45.87058966417344],[126.4897842740157,45.87058167474203],[126.4897884181473,45.87058210937072],[126.4897742105279,45.87060015571375],[126.4897741966053,45.87059852880604],[126.4897729354714,45.87058723569854],[126.4897678694138,45.87058490679236],[126.4897719978628,45.87059862837492],[126.4897656101268,45.87059305105623],[126.4897700666089,45.87058462645604],[126.4897753581734,45.87058306777976],[126.489771372817,45.87058606719324],[126.489773542167,45.87058253304142],[126.4897756401443,45.87058577821201],[126.4897684519181,45.87059249686718],[126.4897666506073,45.87059367942021],[126.489767041851,45.87059403933438],[126.4897696357044,45.87059475159855],[126.489773496319,45.87059229502799],[126.4897734924517,45.87059184310918],[126.4897723125327,45.87059004029665],[126.4897688338802,45.87059177217618],[126.4897747723618,45.87059021079845],[126.4897703050514,45.87059737002615],[126.4897700648481,45.87059954031702],[126.4897708419214,45.87059962745899],[126.4897649547023,45.87059205953631],[126.4897644410365,45.87059251361627],[126.4897778218697,45.87058369020029],[126.4897846118884,45.87057579817657],[126.4897683697164,45.87059801081693],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897682225433,45.87059593253071],[126.4897733615214,45.87059166288196],[126.4897814554297,45.87058494044432],[126.489784563722,45.87058528901186],[126.4897817095553,45.87058439706112],[126.4897813167646,45.87058385637948],[126.4897813167646,45.87058385637948],[126.4897821031195,45.87058502812651],[126.489781435319,45.8705825904665],[126.4897796316883,45.87058350186847],[126.4897813136707,45.87058349484445],[126.489777030874,45.87058197615065],[126.4897802816985,45.87058386070197],[126.489777470847,45.87058803024178],[126.4897801175084,45.87057979397299],[126.4897776994477,45.87058450419445],[126.4897776994477,45.87058450419445],[126.4897776994477,45.87058450419445],[126.4897784332057,45.87057952984571],[126.4897739823524,45.87057349250403],[126.4897809015428,45.87058069456875],[126.4897809015428,45.87058069456875],[126.489771724827,45.87056672290699],[126.48977999586,45.87058069835092],[126.4897724879772,45.87056518314122],[126.4897844910136,45.87057679293826],[126.4897793667339,45.87058277987899],[126.4897770417027,45.87058324152333],[126.4897792497264,45.87058422655949],[126.4897801438069,45.87058286702091],[126.4897800252525,45.87058413293387],[126.4897800252525,45.87058413293387],[126.489777548407,45.87058197398943],[126.489771441096,45.87057892633575],[126.4897670459329,45.87057939662463],[126.4897670459329,45.87057939662463],[126.4897670459329,45.87057939662463],[126.4897627484412,45.87057616063886],[126.4897627484412,45.87057616063886],[126.4897635958989,45.87058447270322],[126.4897669086004,45.87059358795585],[126.4897641938718,45.87059387045329],[126.4897660230274,45.87059594171571],[126.4897671665935,45.87059349649152],[126.4897742051136,45.87059952302742],[126.4897708331997,45.87061372786609],[126.4897743706378,45.87061886515237],[126.4897744541734,45.87062862659857],[126.4897744572673,45.8706289881336],[126.4897665942738,45.87063245567474],[126.4897625553291,45.87064431323679],[126.4897613488958,45.87065453200467],[126.4897747589241,45.87066423780032],[126.4897748331782,45.87067291464133],[126.4897748169351,45.87067101658236],[126.4897748262169,45.87067210118748],[126.4897652905138,45.8706766603576],[126.4897703851981,45.87068233346297],[126.4897707601995,45.87068079531813],[126.4897668763746,45.87068054037576],[126.4897749043384,45.87068122994727],[126.4897733416813,45.87068006144203],[126.4897691114734,45.8706846888437],[126.4897746107648,45.87067716375868],[126.4897748540621,45.87067535500285],[126.4897748826809,45.87067869920199],[126.4897748826809,45.87067869920199],[126.4897660379817,45.87068840754498],[126.489774937598,45.87068511644896],[126.4897707391023,45.87069344958486],[126.4897750149461,45.87069415482499],[126.4897751216865,45.87070662778385],[126.4897665318828,45.87071588312744],[126.4897752129573,45.87071729306749],[126.4897667378397,45.87072483057936],[126.4897753537311,45.87073374291173],[126.4897504066717,45.87073664908445],[126.4897437444654,45.87074435979821],[126.4897297888216,45.87074649697293],[126.4897187028185,45.87075133377348],[126.4897148213091,45.87075134998064],[126.4897052036104,45.87074632846721],[126.4896955075711,45.87074727282134],[126.4896797428736,45.87074968870422],[126.4896628090821,45.87075156714398],[126.4896529759247,45.87075160819708],[126.4896491317787,45.87074086819431],[126.4896502119438,45.8707310115029],[126.48965220166,45.87072160294911],[126.4896630196163,45.87071568263022],[126.4896780353083,45.87070133879415],[126.4896771551433,45.87070432523963],[126.4896808531329,45.87069798271065],[126.4896928672404,45.87069576326137],[126.4897069429761,45.87069254094583],[126.4897222831562,45.8706858786432],[126.4897344560326,45.87068709323221],[126.4897428757937,45.87070332773226],[126.4897494099395,45.87071089295455],[126.4897450279142,45.87071289976653],[126.4897396332493,45.87071753202954],[126.4897314060662,45.87072380308562],[126.4897126609141,45.8707256890963],[126.489719156935,45.8707439201442],[126.4897182783182,45.87074708735751],[126.4896834076126,45.87073945967351],[126.4896782951454,45.87073170773783],[126.4896760445849,45.87072575159291],[126.4896916778069,45.87070797047122],[126.4896863638014,45.87070690801458],[126.4896951642026,45.87070714243164],[126.4897058821559,45.87070465723183],[126.4897155106748,45.87071094411793],[126.4897257598181,45.87071415525411],[126.489707651533,45.87071486357384],[126.4897099036431,45.87072100048564],[126.4896878594786,45.87073049277409],[126.4896860512016,45.870730861872],[126.4896803846163,45.8707339586887],[126.489680376883,45.87073305485109],[126.4896798701752,45.87073432238455],[126.4896798779084,45.87073522622214],[126.4896800057454,45.87073504491443],[126.4896785825257,45.87073505085658],[126.4896758500034,45.87073325452545],[126.489676756462,45.87073334112785],[126.4896763683111,45.87073334274843],[126.4896745476608,45.87073226570599],[126.4896829483151,45.87073114598815],[126.4896777861176,45.87073270411997],[126.489692819902,45.8707507187487],[126.4896964372306,45.87075007093639],[126.4896951403008,45.87074971480349],[126.4896931724788,45.87074655947509],[126.4896951799755,45.87073922974709],[126.4896958245735,45.87073895589474],[126.489696105768,45.87074157594336],[126.4896946825481,45.87074158188572],[126.4896823903303,45.87074154282161],[126.4896638441513,45.87075156282256],[126.4896625000503,45.87074569327989],[126.4896511019178,45.87074429467476],[126.4896484291846,45.87073436326432],[126.489630740997,45.87072368105731],[126.4896370467694,45.87071967770471],[126.4896551295386,45.87071598673207],[126.4896581728808,45.87070874306705],[126.4896763664689,45.8707028823406],[126.4896836382403,45.87070592513746],[126.4896891854925,45.8707040038497],[126.4896910332092,45.87070824432353],[126.4897008377446,45.87070485906807],[126.489713141555,45.87070625388662],[126.489723751779,45.87070629997066],[126.4897461436398,45.87070720072721],[126.4897314415416,45.87068258647009],[126.4897314415416,45.87068258647009],[126.4897314415416,45.87068258647009],[126.4897751781507,45.8707132257983],[126.4897681270264,45.87072084775068],[126.4897572704078,45.87072224889147],[126.4897503115383,45.87072553188198],[126.4897388327499,45.87072982800275],[126.4897309511781,45.87073112633122],[126.4897282772085,45.87075129379523],[126.4897244949201,45.87074778449586],[126.489716602518,45.87074781745069],[126.4897091920753,45.87074369059109],[126.4897061355905,45.87074939773347],[126.4896905832594,45.87074638951179],[126.4896678550444,45.87075154607695],[126.489671889377,45.87073914621579],[126.4896598829923,45.87074226950051],[126.4896488474933,45.87073788661052],[126.4896485639812,45.87073499541048],[126.4896415068989,45.87072679965673],[126.4896510155559,45.87071907706527],[126.4896578249413,45.87071344464316],[126.4896647698953,45.87070853475057],[126.4896758963463,45.87069330328248],[126.489671702475,45.87070217871737],[126.4896771033306,45.87069826952763],[126.4896803425588,45.87069879832531],[126.489683185903,45.87069842490588],[126.4896981441239,45.87069248729718],[126.4897068236464,45.87069371647496],[126.4897150925906,45.87069232614368],[126.4897265713732,45.87068803002532],[126.4897345678507,45.87066991923715],[126.4897466391841,45.87067438818052],[126.4897516137642,45.87068114643212],[126.4897702826727,45.8707005920631],[126.4897669836722,45.87070819834667],[126.4897751951673,45.87071521424103],[126.4897752578193,45.87072253532556],[126.4897680448245,45.87072636170031],[126.4897734874436,45.87072733322907],[126.4897497659411,45.8707373748559],[126.4897338182763,45.87074864943525],[126.4897242663154,45.87075131054299],[126.4897137862399,45.87075135430254],[126.4897128774608,45.87075099654916],[126.4896945786851,45.87074456509002],[126.4897019697928,45.87074643235657],[126.4896810230277,45.87073296176634],[126.4896757865911,45.87072584305706],[126.4896702305893,45.87074186475105],[126.4896732280656,45.87074438307202],[126.4896688901136,45.87075154175546],[126.4896835836331,45.87072978753072],[126.4896776116427,45.87074255703028],[126.4896891378478,45.87072867969682],[126.4896893703213,45.87072560556854],[126.489691721653,45.87072821697384],[126.4896972689071,45.87072629568569],[126.4896972689071,45.87072629568569],[126.4896924770741,45.87072577337104],[126.489695424049,45.87073751137522],[126.4897020506864,45.87072564301126],[126.4897106420491,45.87071656844],[126.4897241700922,45.8707098233166],[126.4897484501139,45.87070456987324],[126.489733587472,45.87069143543454],[126.4897480956601,45.87067826873922],[126.489755173079,45.87067371983592],[126.4897581212713,45.87065535896568],[126.4897644125461,45.87063454368624],[126.4897606263961,45.87063058246915],[126.4897666695146,45.87062612827118],[126.489718287327,45.87061204915635],[126.489718287327,45.87061204915635],[126.489718287327,45.87061204915635],[126.489718287327,45.87061204915635],[126.489773145296,45.87059663506945],[126.4897690707756,45.87060433497876],[126.4897741989258,45.87059879995732],[126.489774361356,45.87061778054721],[126.4897743768256,45.87061958822245],[126.4897744433447,45.87062736122591],[126.489763556364,45.87064033202899],[126.4897587279688,45.87065065630869],[126.489774782902,45.8706670396969],[126.4897689704879,45.8706833336276],[126.4897738350249,45.8706923520125],[126.4897750219074,45.87069496827882],[126.489765588298,45.87071145810527],[126.4897544216486,45.87072198962642],[126.4897399739995,45.87072711162756],[126.4897129838719,45.87073318986793],[126.4897084714564,45.8707502014632],[126.4896869156639,45.87074116237948],[126.489670500183,45.87074312904333],[126.4896626459157,45.87073249617046],[126.4896553548098,45.87072719377826],[126.4896541888109,45.87072701787226],[126.4896573947889,45.87072366016886],[126.4896685336179,45.87070987484171],[126.4896744388622,45.87070442696744],[126.4896822848583,45.87069897098987],[126.4896948018064,45.87069503208809],[126.4897049975832,45.87069200674666],[126.4897084948049,45.87069244407932],[126.4897108283483,45.87069297665774],[126.4897055746666,45.87069896413535],[126.4896943577406,45.87070362070624],[126.489683866074,45.87070230870661],[126.4896696508979,45.87070435657062],[126.4896541227981,45.87068905561213],[126.489630281437,45.87068508773118],[126.4896232516804,45.87066496077939],[126.4896269450342,45.87065807594954],[126.4896213397921,45.87065321845208],[126.4896361096069,45.87065550685414],[126.4896358247994,45.87063734025799],[126.4896404508975,45.87063361507836],[126.4896432919189,45.87063297050866],[126.4896423754096,45.87063170891707],[126.4896466584536,45.87061813298731],[126.4896209825599,45.87061146115353],[126.4896208673488,45.87059799397277],[126.4896240457384,45.87057628782606],[126.4896210312624,45.87057178106088],[126.4896269553072,45.87055340777054],[126.4896204722295,45.87055180786938],[126.4896327022948,45.87055971086793],[126.4896450186916,45.8705474572055],[126.4896497324081,45.87053885076202],[126.4896473926855,45.87053759511221],[126.4896479094448,45.87053750256781],[126.4896477800617,45.87053750310797],[126.4896485385754,45.87053542104043],[126.4896510249392,45.87052356996409],[126.4896331664532,45.87050809795743],[126.4896272406007,45.87049601083914],[126.4896199186003,45.87048709309371],[126.48961992092,45.87048736424502],[126.48961992092,45.87048736424502],[126.48961992092,45.87048736424502],[126.4896339120522,45.87044401046635],[126.4896357118146,45.87044264714783],[126.48964672585,45.87042940466508],[126.4896500805262,45.87042830601576],[126.4896493034556,45.87042821887295],[126.4896495552619,45.87042740433874],[126.4896500782064,45.87042803486447],[126.4896498132544,45.87042731287465],[126.4896495568085,45.87042758510627],[126.4896487851507,45.87042813064983],[126.4896498171208,45.8704277647935],[126.4896500758866,45.87042776371317],[126.489648656541,45.87042822157375],[126.489648912987,45.87042794934213],[126.4896502029497,45.87042749202171],[126.4896648154842,45.87042652714496],[126.4896879976855,45.87041395695105],[126.4896811357537,45.87041344327906],[126.4896987171296,45.87041165251956],[126.4897087275586,45.87041721471601],[126.4897286486541,45.87041667960052],[126.4897453599269,45.87041905026879],[126.4897445323626,45.87042818281091],[126.4897446888154,45.87043134570241],[126.4897436514319,45.87043107887324],[126.4897427442047,45.87043090188757],[126.4897431300332,45.87043062911549],[126.4897445532451,45.87043062317255],[126.4897455999097,45.87043197460687],[126.4897436535309,45.87044644465348],[126.4897640852071,45.87044509391684],[126.4897730626921,45.87046602621002],[126.4897731493209,45.87047614919154],[126.4897683915412,45.87047960376555],[126.4897732947337,45.87049314133905],[126.4897517656629,45.87051745495604],[126.4897552791163,45.87051979034639],[126.4897554061791,45.87051951865482],[126.4897483945215,45.87053175017802],[126.4897481697863,45.87053572814415],[126.4897486935063,45.87053644905316],[126.4897501190413,45.87053671426145],[126.4897501174944,45.87053653349391],[126.4897494690318,45.87053635542776],[126.4897481752003,45.87053636083049],[126.4897519273116,45.87053634516253],[126.489751022403,45.87053643932821],[126.489748816702,45.87053572544279],[126.4897477800899,45.87053554899746],[126.4897481713331,45.87053590891167],[126.4897482999428,45.87053581798764],[126.4897494721255,45.87053671696282],[126.4897465666953,45.87054495431151],[126.4897413354604,45.87055356291994],[126.489763701736,45.87055148100915],[126.4897605445182,45.87058102968716],[126.4901349299623,45.870573681069],[126.4901316890533,45.87056756920471],[126.4901319718155,45.87057037001977],[126.4901332401038,45.8705673819487],[126.4901421450974,45.87056472351049],[126.4901564672638,45.87054495928999],[126.4901618409765,45.87053788664593],[126.490162926466,45.87052866263599],[126.4901649228417,45.87055025678359],[126.4901829645932,45.87052668076034],[126.4901837083781,45.87052288139793],[126.4901828034704,45.87052297556703],[126.4901834527084,45.87052324401447],[126.4901838408577,45.87052324239218],[126.4901843607126,45.8705235113804],[126.4901843653574,45.87052405368294],[126.4901881081779,45.87052295339557],[126.4902058584375,45.87052577158885],[126.4902251241344,45.87052424486932],[126.4902525216098,45.87052042447977],[126.4902320853621,45.87050613837305],[126.4902374807398,45.87050159647063],[126.4902843956114,45.87051052944134],[126.4903051542047,45.87051713129669],[126.4903131937666,45.8705191765868],[126.4903139700652,45.87051917334132],[126.4903147533329,45.87051998354966],[126.4903130178556,45.87052884873048],[126.4903179203447,45.87055739052419],[126.4903323950612,45.87054033725361],[126.490342958731,45.870550054883],[126.4903308090411,45.87056664649935],[126.4903252765379,45.87057028510949],[126.4903184656889,45.87057573680366],[126.4903178218702,45.87057610104328],[126.4903176893896,45.87057574004918],[126.490317425977,45.87057519882848],[126.4903172996912,45.87057556090441],[126.4903171741797,45.87057601336412],[126.4903183486954,45.87057718348466],[126.4903353265512,45.870580456822],[126.4903390864091,45.87058134497229],[126.4903416640068,45.87058015916462],[126.4903911317746,45.87058501400316],[126.4903496935117,45.87058102946329],[126.4903354226358,45.87057656977962],[126.4903148313432,45.87057439619301],[126.4903045132072,45.87057823558339],[126.4902959467409,45.8705902024792],[126.4902940733578,45.87059807397925],[126.4902751717843,45.87059679719147],[126.4902661955587,45.87059114033457],[126.490266931596,45.87058643713414],[126.4902648614639,45.87058644578789],[126.4902583566851,45.87058231517785],[126.4902591391787,45.87058303500279],[126.4902607049402,45.8705845650364],[126.4902755615596,45.87058188170842],[126.4902786806948,45.87058349563507],[126.4902698710197,45.87058217665783],[126.4902650838396,45.87058219666967],[126.4902617160039,45.87058175881317],[126.4902641982879,45.87058455043333],[126.4902702816236,45.87058479616412],[126.4902788193696,45.87058457969928],[126.4902789472042,45.87058439839089],[126.4902724028584,45.87059075283811],[126.4902719922543,45.87058813333181],[126.4902630121591,45.87058202455587],[126.4902614433006,45.87058013298728],[126.4902655068316,45.87058626231608],[126.4902628998098,45.87058401353934],[126.4902601866329,45.87058447681605],[126.490271843514,45.87058587427882],[126.4902661785247,45.87058915189196],[126.4902599449002,45.87058646634038],[126.4902879590482,45.87059421289951],[126.4902789578924,45.8706158530193],[126.4903021709362,45.87062199267952],[126.4902930365986,45.87062808679462],[126.4902854324045,45.87063155328956],[126.4902731486507,45.87064760313802],[126.4902638167864,45.8706457440213],[126.4902508645106,45.87064417119872],[126.4902244764927,45.87064500459763],[126.4902048822155,45.87065349248869],[126.4902126003172,45.87064821778321],[126.49020246351,45.87064301770732],[126.4901935833789,45.87063347380249],[126.4901873389148,45.87062952287441],[126.4901861698198,45.8706289854388],[126.4901803011206,45.87062358674792],[126.4901609889418,45.87061969043465],[126.4901589599443,45.87060939479754],[126.4901652099296,45.87059888378547],[126.4901664193842,45.87058902654854],[126.4901663512623,45.87058107277797],[126.4901603377048,45.87057386695162],[126.4901557010281,45.87054613752323],[126.4901662019653,45.87054853408476],[126.4901606137178,45.87054566505645],[126.4901726929041,45.87053594316428],[126.4901834015133,45.87053237331472],[126.4901955333354,45.87052879751562],[126.4902219507185,45.87053139870635],[126.4902240270422,45.87053211312346],[126.4902240270422,45.87053211312346],[126.4902705407035,45.87052441657016],[126.4903069362118,45.87049859451317],[126.4902910739755,45.87050471675502],[126.4902992607276,45.87050884033163],[126.490305932052,45.87051730881878],[126.4903123779091,45.87052966488883],[126.4903163592945,45.87054130816586],[126.4903605338785,45.87056263557957],[126.4903370760258,45.87055812392055],[126.4903441479019,45.8705680369226],[126.4903399502745,45.87057646046295],[126.4903352521494,45.8705868746095],[126.490337108309,45.87062229854979],[126.4903453989099,45.87062343891812],[126.4903283970348,45.87061736368599],[126.4903168873392,45.87061804451448],[126.4903047833817,45.8706248741416],[126.4902968026536,45.87062969801631],[126.4902866665425,45.87063968295949],[126.4902739203826,45.87063196296245],[126.4902526898976,45.87063069590663],[126.4902380773204,45.87063166085868],[126.4902259524466,45.87063605011613],[126.4902180376092,45.87063346197663],[126.4901958673781,45.87062822181065],[126.4901861109847,45.87062211627337],[126.4901771409571,45.87061718247951],[126.4901590350321,45.8706181620219],[126.4901656592343,45.87060602247998],[126.4901662821533,45.8706032178798],[126.490162444886,45.87059329134819],[126.4901629020387,45.8705862392522],[126.4901656431913,45.87057393516493],[126.4901635979384,45.87056174146891],[126.4901687989181,45.87054960787488],[126.4901642256088,45.87054438454309],[126.490162427512,45.87053083400914],[126.4901514276239,45.87053060882038],[126.4901769153259,45.87053041191013],[126.4901845063526,45.87052540889854],[126.4901952064433,45.87052084482655],[126.4902063426804,45.87052188292414],[126.4902063426804,45.87052188292414],[126.4902063426804,45.87052188292414],[126.4902063426804,45.87052188292414],[126.4902670017568,45.87050400390363],[126.4902871878377,45.87050419067901],[126.4902840368856,45.8705139656467],[126.4902938428991,45.87051076110802],[126.4902756982194,45.8705223161091],[126.4902788336113,45.87052582809466],[126.4902796757251,45.87053350746869],[126.4902804984816,45.87053892724879],[126.4902956912147,45.87056037584005],[126.4902757228426,45.87055539764547],[126.4902837669799,45.87057307986818],[126.4902821120985,45.87057625033102],[126.490278300288,45.87058440109524],[126.4902752547102,45.87059137362531],[126.4902748037659,45.8705991487917],[126.4902852651544,45.8706120303986],[126.490277181369,45.87061992786049],[126.4902743806187,45.87062527240114],[126.4902638091237,45.87062974555592],[126.4902609959026,45.87064873858402],[126.4902568796359,45.87065155778766],[126.4902482736616,45.87065891510835],[126.490251129388,45.8706599878149],[126.4902511340335,45.87066053011742],[126.4902511332592,45.87066043973367],[126.4902515245065,45.87066079964616],[126.4902507427862,45.87066017020494],[126.4902516538899,45.87066079910531],[126.4902503600557,45.87066080451374],[126.4902506768703,45.8706840488275],[126.4902171492577,45.8707344731642],[126.4901728928089,45.87076622179718],[126.4900924265385,45.87078209610688],[126.489560008049,45.87078209610688]]}}
            */    
        } ,  
        "layout": {
            "line-join": "round",
            "line-cap": "round"
        },
        "paint": {
            "line-color": "#FF0000",
            "line-width": 3
        }
    });
});
</script>

</body>
</html>