(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   [ferry-front.views.header :refer [header]]
   [ferry-front.views.navigation :refer [main-navigation]]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.views.timetables :as timetables]

   [ferry-front.leaflet.core :refer [leaflet]]))


(def norra-linjen-json "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"LineString\",\"coordinates\":[[21.0380781,60.3565814],[21.0382179,60.3553567],[21.0375569,60.3546156],[21.0375128,60.3521958],[21.0374247,60.3506916],[21.0356178,60.3480535],[21.0327973,60.3442814],[21.0324888,60.3435836],[21.0326651,60.3390258],[21.0327091,60.3352745],[21.0324888,60.3344238],[21.0283902,60.330432],[21.0273766,60.3297557],[21.011379,60.3204393],[21.0089551,60.3187808],[21.0058702,60.3181479],[21.00097,60.3178095],[20.9959478,60.3174626],[21.000977,60.3174574],[21.0059969,60.3174981],[21.0084704,60.3170131],[21.0093077,60.3162711],[21.0115553,60.3108582],[21.0123926,60.3088282],[21.0120841,60.3081733],[21.0114231,60.30706],[21.0092195,60.305379],[21.0035345,60.3011216],[20.9923405,60.2929327],[20.9911947,60.2922119],[20.9895641,60.2916659],[20.9871843,60.291513],[20.9816755,60.2913383],[20.9735224,60.2912509],[20.9703053,60.2912073],[20.9617997,60.2909233],[20.9577452,60.290967],[20.9529856,60.2911199],[20.9404255,60.2918406],[20.9299367,60.2918188],[20.901335,60.2917314],[20.892565,60.2916441],[20.8833983,60.2914038],[20.8783302,60.2911636],[20.8670481,60.2901589],[20.8640954,60.290006],[20.8625529,60.2897657],[20.8477453,60.2853534],[20.8467757,60.2851786],[20.8254456,60.2847635],[20.8234624,60.2851349],[20.8227132,60.2856155],[20.8020001,60.2950947],[20.800678,60.2954223],[20.7984745,60.2954223],[20.7952573,60.2952694],[20.7934063,60.2947672],[20.7915213,60.2932543],[20.7907349,60.2914748],[20.7901287,60.2904026],[20.7900959,60.2915048],[20.7905234,60.2933364],[20.7909943,60.2964539],[20.7911361,60.2988542],[20.7911137,60.300018],[20.7898398,60.3012264],[20.7768164,60.307132],[20.7536839,60.3173201],[20.7513513,60.3183138],[20.7532177,60.3170526],[20.7535002,60.3157748],[20.7531392,60.3143331],[20.7518576,60.3130214],[20.7507718,60.3124468],[20.7443818,60.3089555],[20.7406149,60.308118],[20.7362386,60.3071507],[20.7341915,60.3062021],[20.729194,60.3021169],[20.7249325,60.2987367],[20.7220025,60.2970702],[20.7073801,60.2914643],[20.6959614,60.286911],[20.6851264,60.2825724],[20.6744544,60.2782402],[20.6636269,60.2738691],[20.6546511,60.2702029],[20.6512201,60.269176],[20.6304683,60.2648218],[20.612754,60.2609731],[20.5953818,60.2571799],[20.5784603,60.2534389],[20.5655181,60.2506334],[20.5502005,60.2491236],[20.5301966,60.2473464],[20.5190451,60.2462964],[20.4646746,60.2400221],[20.4491392,60.2381075],[20.4469076,60.2373187],[20.4246989,60.2253099],[20.4220811,60.2244318],[20.4192272,60.224069],[20.4144713,60.2240448],[20.4136621,60.2242177]]}\n]}")
(def norra-waypoints "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"Point\",\"coordinates\":[20.7513513,60.3183138,0]},\n{\"type\":\"Point\",\"coordinates\":[21.0380781,60.3565814,0]},\n{\"type\":\"Point\",\"coordinates\":[20.7901287,60.2904026,0]},\n{\"type\":\"Point\",\"coordinates\":[20.9959478,60.3174626,0]},\n{\"type\":\"Point\",\"coordinates\":[20.4136621,60.2242177,0]}\n]}")
(def sodra-linjen-json "{\"type\":\"GeometryCollection\", \"geometries\": [{\"type\":\"LineString\",\"coordinates\":[[20.8073747,60.0647341],[20.7828927,60.0585919],[20.7910243,60.0369224],[20.8011559,60.0251313],[20.8250786,60.0312659]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.8917824,59.9455971],[20.8915331,59.9641436],[20.9621996,59.966747],[21.0546149,59.9733263],[21.0771815,59.9880561],[21.1244003,60.0290056],[21.206846,60.0715985],[21.3081992,60.1202512],[21.4408248,60.1763707],[21.4937673,60.1898195],[21.5485938,60.1956486],[21.5865524,60.1846737]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.2965061,60.1176075],[20.3231026,60.1136252],[20.3779227,60.1143181],[20.4224946,60.1039583],[20.4502996,60.1015393],[20.4815414,60.1139846],[20.5106347,60.1115177]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.5106347,60.1115177],[20.58542,60.1260053],[20.6717787,60.0967685],[20.6902252,60.0965176],[20.7021309,60.1061828],[20.6823206,60.1100751]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.6823206,60.1100751],[20.7193995,60.1056333],[20.7488286,60.0950868],[20.7714318,60.069322],[20.8073747,60.0647341]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.8250786,60.0312659],[20.7985139,60.0195368],[20.7982403,60.0105233],[20.8286244,59.9818526],[20.8893901,59.9633281],[20.8917824,59.9455971]]}]}")
(def sodra-waypoints "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"Point\",\"coordinates\":[21.5858297,60.1854808,0]},\n{\"type\":\"Point\",\"coordinates\":[20.2965061,60.1176075,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8917824,59.9455971,0]},\n{\"type\":\"Point\",\"coordinates\":[20.5106347,60.1115177,0]},\n{\"type\":\"Point\",\"coordinates\":[20.6823206,60.1100751,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8073747,60.0647341,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8250786,60.0312659,0]}\n]}")
(def tvar-linjen-json "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"LineString\",\"coordinates\":[[20.5106347,60.1115177],[20.5101382,60.1116882],[20.5098807,60.1119949],[20.5071566,60.1147979],[20.5059956,60.1175866],[20.5053358,60.1193847],[20.5071983,60.120962],[20.5120037,60.1236348],[20.5175838,60.1263994],[20.5225899,60.1279191],[20.5300518,60.1301329],[20.5340204,60.1317669],[20.5390447,60.134403],[20.548293,60.1394888],[20.5562548,60.1443089],[20.563452,60.1486619],[20.5656812,60.1499912],[20.5689985,60.1516824],[20.5759399,60.1531015],[20.585522,60.1549544],[20.6047266,60.1586207],[20.6089849,60.1595512],[20.6129846,60.1607347],[20.6311056,60.1660636],[20.6504423,60.1717131],[20.6679828,60.1769587],[20.6755972,60.1792722],[20.6917854,60.18419],[20.694851,60.1847959],[20.6981319,60.1849405],[20.7143099,60.1855689],[20.7301736,60.1860495],[20.7439247,60.1863584],[20.7472614,60.1869035],[20.7501185,60.1878487],[20.7509113,60.1891752],[20.750008,60.1905966],[20.7471455,60.1929042],[20.7376977,60.2006519],[20.7267135,60.2096711],[20.7224552,60.2133099],[20.7208995,60.2148816],[20.720656,60.2165162],[20.7216001,60.2176779],[20.7233221,60.2184645],[20.7260151,60.2187986],[20.726748,60.2191638]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.5106347,60.1115177],[20.5101845,60.1117745],[20.5100264,60.1120331],[20.5097988,60.112387],[20.5095639,60.1128965],[20.5099676,60.1136507],[20.5109973,60.1142934],[20.5218311,60.1159739],[20.5349329,60.1165649],[20.5854096,60.1262996],[20.6719552,60.0970062],[20.6901729,60.096827],[20.6986709,60.099792],[20.7003792,60.1007245],[20.7023711,60.1031654],[20.702613,60.1046925],[20.7016955,60.1059882],[20.6974641,60.1070021],[20.6907213,60.108339],[20.6826587,60.1087314],[20.6823206,60.1100751],[20.6831137,60.109296],[20.6910002,60.1091837],[20.7018892,60.1085005],[20.7139054,60.1100617],[20.7137338,60.1116014],[20.7117597,60.1147877],[20.6975118,60.129885],[20.6958381,60.1317658],[20.6957093,60.1325096],[20.6970397,60.1377922],[20.6954089,60.1398221],[20.6872121,60.149752],[20.6796161,60.1588545],[20.6724063,60.1675404],[20.6693593,60.1712336],[20.6691447,60.1721728],[20.6693593,60.1734962],[20.6700889,60.1751611],[20.6708184,60.1761002],[20.6730071,60.1779613],[20.6755972,60.1792722],[20.6917854,60.18419],[20.694851,60.1847959],[20.6981319,60.1849405],[20.7143099,60.1855689],[20.7301736,60.1860495],[20.7439247,60.1863584],[20.7472614,60.1869035],[20.7501185,60.1878487],[20.7509113,60.1891752],[20.750008,60.1905966],[20.7471455,60.1929042],[20.7376977,60.2006519],[20.7267135,60.2096711],[20.7224552,60.2133099],[20.7208995,60.2148816],[20.720656,60.2165162],[20.7216001,60.2176779],[20.7233221,60.2184645],[20.7260151,60.2187986],[20.726748,60.2191638]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.2965061,60.1176075],[20.2956341,60.1185749],[20.296196,60.1194326],[20.2975799,60.1196944],[20.2987628,60.1193987],[20.3030459,60.1180952],[20.3092566,60.116341],[20.3143052,60.1149524],[20.3196903,60.1141978],[20.3231595,60.1139383],[20.3334618,60.1137766],[20.3437031,60.1139467],[20.3536106,60.1147128],[20.3633922,60.1156505],[20.3693561,60.1155631],[20.3781043,60.1146195],[20.3861633,60.1130615],[20.3950079,60.1112676],[20.4108233,60.1072412],[20.4227285,60.104251],[20.4312204,60.1029812],[20.4373422,60.1024726],[20.4502828,60.1017936],[20.4551155,60.102209],[20.4581541,60.102782],[20.4609665,60.1038128],[20.4813698,60.1141395],[20.5017707,60.1159102],[20.5049971,60.115412],[20.5065844,60.1146791],[20.509544,60.1119138],[20.5098779,60.1116024],[20.5106347,60.1115177],[20.5101382,60.1116882],[20.5098807,60.1119949],[20.5071566,60.1147979],[20.5059956,60.1175866],[20.5053358,60.1193847],[20.5071983,60.120962],[20.5120037,60.1236348],[20.5175838,60.1263994],[20.5225899,60.1279191],[20.5300518,60.1301329],[20.5340204,60.1317669],[20.5390447,60.134403],[20.548293,60.1394888],[20.5562548,60.1443089],[20.563452,60.1486619],[20.5656812,60.1499912],[20.5689985,60.1516824],[20.5759399,60.1531015],[20.585522,60.1549544],[20.6047266,60.1586207],[20.6089849,60.1595512],[20.6129846,60.1607347],[20.6311056,60.1660636],[20.6504423,60.1717131],[20.6679828,60.1769587],[20.6755972,60.1792722],[20.6917854,60.18419],[20.694851,60.1847959],[20.6981319,60.1849405],[20.7143099,60.1855689],[20.7301736,60.1860495],[20.7439247,60.1863584],[20.7472614,60.1869035],[20.7501185,60.1878487],[20.7509113,60.1891752],[20.750008,60.1905966],[20.7471455,60.1929042],[20.7376977,60.2006519],[20.7267135,60.2096711],[20.7224552,60.2133099],[20.7208995,60.2148816],[20.720656,60.2165162],[20.7216001,60.2176779],[20.7233221,60.2184645],[20.7260151,60.2187986],[20.726748,60.2191638]]}\n]}")
(def tvar-waypoints "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"Point\",\"coordinates\":[20.5106347,60.1115177,0]},\n{\"type\":\"Point\",\"coordinates\":[20.6823206,60.1100751,0]},\n{\"type\":\"Point\",\"coordinates\":[20.726748,60.2191638,0]},\n{\"type\":\"Point\",\"coordinates\":[20.2965061,60.1176075,0]},\n{\"type\":\"Point\",\"coordinates\":[20.5106347,60.1115177,0]},\n{\"type\":\"Point\",\"coordinates\":[20.726748,60.2191638,0]}\n]}")
(def url "https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw")

(defonce app-state (atom {:text "Svg test!" :fill-number 0 :url sodra-linjen-json}))

(def jsons [{:type     :json
             :url      sodra-linjen-json
             :color    "red"
             :linejoin "round"
             :weight   3
             :opacity  0.50}
            {:type     :json
             :url      norra-linjen-json
             :color    "blue"
             :linejoin "round"
             :weight   3
             :opacity  0.50}
            {:type     :json
             :url      tvar-linjen-json
             :color    "orange"
             :linejoin "round"
             :weight   3
             :opacity  0.50}])

(def json-transparent [{:type     :json
                        :url      tvar-linjen-json
                        :color    "#200080"
                        :linejoin "round"
                        :weight   0
                        :opacity  0.00}])

(def jsons-norra [{:type     :json
                   :url      norra-linjen-json
                   :color    "yellow"
                   :linejoin "round"
                   :weight   4
                   :opacity  0.65}])

(def jsons-sodra [{:type     :json
                   :url      sodra-linjen-json
                   :color    "yellow"
                   :linejoin "round"
                   :weight   4
                   :opacity  0.65}])

(def jsons-tvar [{:type     :json
                  :url      tvar-linjen-json
                  :color    "yellow"
                  :linejoin "round"
                  :weight   4
                  :opacity  0.65}])


(def view-position (atom [59.75
                          21.00]))
(def zoom-level (atom 9))



(defn main-panel []
  (let [line @(re-frame/subscribe [::subs/booking-line])
        line-color (str "#4286f" line)]

    (println "what's the line " line)
    [:div {:class "flex flex-col m-auto max-w-5xl"}
     [header]
     [main-navigation]
     [:div
      [booking-form/booking-form]
      [timetables/booking-timetable]
      [:div {:class "flex justify-center"}
       [leaflet {:id         "kartta"
                 :width      "1000px" :height "1000px"      ;; set width/height as CSS units
                 :view       view-position                  ;; map center position
                 :zoom       zoom-level                     ;; map zoom level

                 ;; The actual map data (tile layers from OpenStreetMap), also supported is
                 ;; :wms type
                 :layers     [{:type        :tile
                               :url         "http://{s}.tile.osm.org/{z}/{x}/{y}.png"
                               :attribution "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors"}]
                 :base-jsons jsons
                 :jsons      (cond
                               (<= line 0) json-transparent
                               (<= line 1) jsons-norra
                               (<= line 2) jsons-sodra
                               (<= line 3) jsons-tvar
                               (<= line 4) jsons-tvar
                               :else json-transparent)
                 :line-color line-color}]




       [:div.actions
        "Control the map position/zoom by swap!ing the atoms"
        [:br]
        [:button {:on-click #(swap! view-position update-in [1] - 0.2)} "left"]
        [:button {:on-click #(swap! view-position update-in [1] + 0.2)} "right"]
        [:button {:on-click #(swap! view-position update-in [0] + 0.2)} "up"]
        [:button {:on-click #(swap! view-position update-in [0] - 0.2)} "down"]
        [:button {:on-click #(swap! zoom-level inc)} "zoom in"]
        [:button {:on-click #(swap! zoom-level dec)} "zoom out"]
        ]]]]))


