(ns ferry-front.leaflet.basic-map
  (:require [reagent.core :as reagent]
            [ferry-front.subs :as subs]
            [re-frame.core :as re-frame]
            ))

(enable-console-print!)

(def sodra-linjen-json "{\"type\":\"GeometryCollection\", \"geometries\": [{\"type\":\"LineString\",\"coordinates\":[[20.8073747,60.0647341],[20.7828927,60.0585919],[20.7910243,60.0369224],[20.8011559,60.0251313],[20.8250786,60.0312659]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.8917824,59.9455971],[20.8915331,59.9641436],[20.9621996,59.966747],[21.0546149,59.9733263],[21.0771815,59.9880561],[21.1244003,60.0290056],[21.206846,60.0715985],[21.3081992,60.1202512],[21.4408248,60.1763707],[21.4937673,60.1898195],[21.5485938,60.1956486],[21.5865524,60.1846737]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.2965061,60.1176075],[20.3231026,60.1136252],[20.3779227,60.1143181],[20.4224946,60.1039583],[20.4502996,60.1015393],[20.4815414,60.1139846],[20.5106347,60.1115177]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.5106347,60.1115177],[20.58542,60.1260053],[20.6717787,60.0967685],[20.6902252,60.0965176],[20.7021309,60.1061828],[20.6823206,60.1100751]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.6823206,60.1100751],[20.7193995,60.1056333],[20.7488286,60.0950868],[20.7714318,60.069322],[20.8073747,60.0647341]]},\n{\"type\":\"LineString\",\"coordinates\":[[20.8250786,60.0312659],[20.7985139,60.0195368],[20.7982403,60.0105233],[20.8286244,59.9818526],[20.8893901,59.9633281],[20.8917824,59.9455971]]}]}")
(def sodra-waypoints "{\"type\":\"GeometryCollection\", \"geometries\": [\n{\"type\":\"Point\",\"coordinates\":[21.5858297,60.1854808,0]},\n{\"type\":\"Point\",\"coordinates\":[20.2965061,60.1176075,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8917824,59.9455971,0]},\n{\"type\":\"Point\",\"coordinates\":[20.5106347,60.1115177,0]},\n{\"type\":\"Point\",\"coordinates\":[20.6823206,60.1100751,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8073747,60.0647341,0]},\n{\"type\":\"Point\",\"coordinates\":[20.8250786,60.0312659,0]}\n]}")
(def url "https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw")

(defn home-render []
  [:div#map {:style {:height "600px" :width "800px"}}])


;var map = L.map('map').setView([51.505, -0.09], 13);
;
;L.tileLayer('http://{s}.tiles.mapbox.com/v3/MapID/{z}/{x}/{y}.png', {
;                                                                     attribution: 'Map data &copy; [...]',
;                                                                     maxZoom: 18
;                                                                     }).addTo(map);

(defn home-did-mount [] 
  (let [map (.setView (.map js/L "map") #js [60.256166965894586
                                             20.71746826171875] 9)
        stop-routes @(re-frame/subscribe [::subs/stop-routes])
        second (get-in stop-routes [2])
        test-geojson (.parse js/JSON (get-in second [:geometry]))]

    ; add base map
    (.addTo (.tileLayer js/L url
                        (clj->js {:attribution "'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>"
                                  :id          "mapbox.streets"
                                  :maxZoom     18}))
            map)

    ; add static line södra linje
    (.addTo (.geoJson js/L (.parse js/JSON sodra-linjen-json)
                      (clj->js {:style
                                {:color   "#400080"
                                 :linejoin "round"
                                 :dashArray "3"

                                 :weight  2.5
                                 :opacity 0.80}}))
            map)

    ;add static points södra linje
    (.addTo (.geoJson js/L (.parse js/JSON sodra-waypoints)
                      (clj->js {:style
                                {:color   "#400080"
                                 :weight  2.5
                                 :opacity 0.80}}))
            map)

    ; add highlighted geometry from db
    (.addTo (.geoJson js/L test-geojson
                      (clj->js {:style
                                {:color   "#ffff00"
                                 :weight  5.5
                                 :opacity 0.65}}))
            map)))

(defn home []
  (reagent/create-class {:reagent-render       home-render
                         :component-did-mount  home-did-mount}))