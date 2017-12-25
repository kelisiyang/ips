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
    <script src='${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js'></script>
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
													<TD>设置开始时间</TD>	
													<TD><input type="text" id="starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="starttime" style="width:150px"/></TD>
													<TD>设置结束时间</TD>	
													<TD><input type="text" id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endtime" style="width:150px"/></TD>
													
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
            "data":  ${jsonString}
            /* "C:/Users/lenovo/esktop/ips-master/WebRoot/geojson/tracemap.geojson" */
            /* {"type": "Feature","geometry": {"type": "LineString","coordinates":[[126.4896550513974,45.87066148370323],[126.4896528791891,45.87064956168927],[126.4896866762301,45.87074342305391],[126.4896932438612,45.87073978015285]]}} */
              /*  {"type": "Feature","geometry": {"type": "LineString","coordinates":[[126.4896550513974,45.87066148370323],[126.4896528791891,45.87064956168927],[126.4896866762301,45.87074342305391],[126.4896932438612,45.87073978015285],[126.4896979511665,45.87074554526577],[126.4897201107977,45.87073442552746],[126.4897043654392,45.87073910100774],[126.4897309813411,45.87073465129785],[126.4897391410162,45.87073561148285],[126.4897485813792,45.87073502974063],[126.4897573748239,45.87073445069905],[126.4897586392687,45.87073101071334],[126.4897591351463,45.87072847780691],[126.489751388373,45.87073040828281],[126.4897525249813,45.87072714960494],[126.4897478942424,45.87073033248649],[126.489739347964,45.87072955469042],[126.4897355749574,45.8707271299965],[126.4897227938241,45.8707304372969],[126.489690142521,45.8707402450367],[126.4897203662445,45.8707491575401],[126.4897117137814,45.87075109179501],[126.4897057515372,45.87073475664477],[126.4897099552296,45.8707421508258],[126.4896877842289,45.87073682017754],[126.4896901316942,45.87073897966407],[126.4897135632769,45.87074041840781],[126.489724042577,45.87074028426451],[126.4897388333014,45.87074501301467],[126.4897531384231,45.87073835503003],[126.4897644793204,45.87073306522677],[126.489759332037,45.87072124602555],[126.4897530814061,45.87071657200365],[126.4897388919739,45.87070650791211],[126.4897246822126,45.87070918846929],[126.489704856368,45.87070574615878],[126.4897037027432,45.87070701639342],[126.4896914244532,45.87070860423795],[126.4897752129573,45.87071729306749],[126.4897667378397,45.87072483057936],[126.4897753537311,45.87073374291173],[126.4897504066717,45.87073664908445],[126.4897437444654,45.87074435979821],[126.4897297888216,45.87074649697293],[126.4897187028185,45.87075133377348],[126.4897148213091,45.87075134998064],[126.4897052036104,45.87074632846721],[126.4896955075711,45.87074727282134],[126.4896797428736,45.87074968870422],[126.4896628090821,45.87075156714398],[126.4896529759247,45.87075160819708],[126.4896491317787,45.87074086819431],[126.4896502119438,45.8707310115029],[126.48965220166,45.87072160294911],[126.4896630196163,45.87071568263022],[126.4896780353083,45.87070133879415],[126.4896771551433,45.87070432523963],[126.4896808531329,45.87069798271065],[126.4896928672404,45.87069576326137],[126.4897069429761,45.87069254094583],[126.4897222831562,45.8706858786432],[126.4897344560326,45.87068709323221],[126.4897428757937,45.87070332773226],[126.4897494099395,45.87071089295455],[126.4897450279142,45.87071289976653],[126.4897396332493,45.87071753202954],[126.4897314060662,45.87072380308562],[126.4897126609141,45.8707256890963],[126.489719156935,45.8707439201442],[126.4897182783182,45.87074708735751],[126.4896834076126,45.87073945967351],[126.4896782951454,45.87073170773783],[126.4896760445849,45.87072575159291],[126.4896916778069,45.87070797047122],[126.4896863638014,45.87070690801458],[126.4896951642026,45.87070714243164],[126.4897058821559,45.87070465723183],[126.4897155106748,45.87071094411793],[126.4897257598181,45.87071415525411],[126.489707651533,45.87071486357384],[126.4897099036431,45.87072100048564],[126.4896878594786,45.87073049277409],[126.4896860512016,45.870730861872],[126.4896803846163,45.8707339586887],[126.489680376883,45.87073305485109],[126.4896798701752,45.87073432238455],[126.4896798779084,45.87073522622214],[126.4896800057454,45.87073504491443],[126.4896785825257,45.87073505085658],[126.4896758500034,45.87073325452545],[126.489676756462,45.87073334112785],[126.4896763683111,45.87073334274843],[126.4896745476608,45.87073226570599],[126.4896829483151,45.87073114598815],[126.4896777861176,45.87073270411997],[126.489692819902,45.8707507187487],[126.4896964372306,45.87075007093639],[126.4896951403008,45.87074971480349],[126.4896931724788,45.87074655947509],[126.4896951799755,45.87073922974709],[126.4896958245735,45.87073895589474],[126.4902821120985,45.87057625033102],[126.490278300288,45.87058440109524],[126.4902752547102,45.87059137362531],[126.4902748037659,45.8705991487917],[126.4902852651544,45.8706120303986],[126.490277181369,45.87061992786049],[126.4902743806187,45.87062527240114],[126.4902638091237,45.87062974555592],[126.4902609959026,45.87064873858402],[126.4902568796359,45.87065155778766],[126.4902482736616,45.87065891510835],[126.490251129388,45.8706599878149],[126.4902511340335,45.87066053011742],[126.4902511332592,45.87066043973367],[126.4902515245065,45.87066079964616],[126.4902507427862,45.87066017020494],[126.4902516538899,45.87066079910531],[126.4902503600557,45.87066080451374]]}} */
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