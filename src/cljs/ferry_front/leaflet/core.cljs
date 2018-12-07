(ns ferry-front.leaflet.core
  (:require [reagent.core :as reagent :refer [atom]]))

;;;;;;;;;
;; Define the React lifecycle callbacks to manage the LeafletJS
;; Javascript objects.

(declare update-leaflet-json-layers)

(defn- leaflet-did-mount [this]
  "Initialize LeafletJS map for a newly mounted map component."
  (let [mapspec (:mapspec (reagent/state this))
        leaflet (js/L.map (:id mapspec))
        view (:view mapspec)
        zoom (:zoom mapspec)
        line-weight (:weight mapspec)]

    (.setView leaflet (clj->js @view) @zoom)

    (doseq [{:keys [type url] :as layer-spec} (:layers mapspec)]
      (let [layer (case type
                    :tile (js/L.tileLayer
                            url
                            (clj->js {:attribution (:attribution layer-spec)}))
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    "#400080"
                                       :linejoin "round"
                                       :weight   line-weight
                                       :opacity  0.50}}))
                    )]
        ;;(.log js/console "L.tileLayer = " layer)
        (.addTo layer leaflet)))

    (doseq [{:keys [type url color linejoin weight] :as layer-spec} (:jsons mapspec)]
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   line-weight
                                       :opacity  0.50}})))]
        (.addTo layer leaflet)))

    (reagent/set-state this {:leaflet        leaflet
                             :geometries-map {}
                             :jsons-map      {}})

    (add-watch view ::view-update
               (fn [_ _ old-view new-view]
                 ;;(.log js/console "change view: " (clj->js old-view) " => " (clj->js new-view) @zoom)
                 (when (not= old-view new-view)
                   (.setView leaflet (clj->js new-view) @zoom))))
    (add-watch zoom ::zoom-update
               (fn [_ _ old-zoom new-zoom]
                 (when (not= old-zoom new-zoom)
                   (.setZoom leaflet new-zoom))))

    (when-let [g (:jsons mapspec)]
      (add-watch g ::layers-update
                 (fn [_ _ _ new-layers]
                   (update-leaflet-json-layers this new-layers))))))

(defn- leaflet-render [this]
  (let [mapspec (-> this reagent/state :mapspec)]
    [:div {:id    (:id mapspec)
           :style {:width  (:width mapspec)
                   :height (:height mapspec)}}]))

;;;;;;;;;;;
;;;;;;;;;;;
;; the updating happens here!
(defn- leaflet-will-update [this old-state new-state]
  "This updates all the geometries and other stuff"
  (let [{:keys [leaflet]} (reagent/state this)
        this-state (reagent/state this)
        mapspec (:mapspec this-state)
        base-jsons (:base-jsons mapspec)
        stops (:stops mapspec)
        old-highlight-json (:jsons mapspec)
        highlight-json (:jsons (second old-state))
        ;;;;;;;;;;;;;ZOOM COORDINATES;;;;;;;;;;;;;;;;;
        url-coord (second highlight-json)
        second-url-coordinate (second url-coord)
        second-multiline-string-coord (second second-url-coordinate)
        parsed-coordlist (.parse js/JSON second-multiline-string-coord)
        keywordized-coordlist (js->clj parsed-coordlist :keywordize-keys true)
        coordinates (:coordinates keywordized-coordlist)
        count (count(first coordinates))
        divided (/ count 2)
        round (Math/round divided)
        zoom-coords (nth (first coordinates) round)
        lat (second zoom-coords)
        lng (first zoom-coords)]

    ; remove the previous highlighted json layer
    (doseq [removed old-highlight-json]
      (.removeLayer leaflet removed))

    ; let's add the now highlighted json to state
    (reagent/set-state this {:jsons highlight-json})

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; Circle geometry
    ; The javascript
    ; L.circle([37.786542, -122.386022], {
    ;          color: "red",
    ;          fillColor: "#f03",
    ;          fillOpacity: 0.5,
    ;          radius: 50.0
    ;      }).addTo(map);

    (doseq [{:keys [lat lng name] :as layer-spec} stops]
      (let [layer (js/L.circle (clj->js [lat
                                         lng])
                               300
                               #js {:color       "black"
                                    :fillColor   "white"
                                    :fillOpacity 0.9})]
        (.addTo layer leaflet)))

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;// create custom icon
    ;    var myIcon = L.icon({
    ;        iconUrl: 'mypic.png',
    ;        iconSize: [38, 95], // size of the icon
    ;        popupAnchor: [0,-15]
    ;        });
    ;
    ;    // create popup contents
    ;    var customPopup = "Some text<br/><img src='http://joshuafrazier.info/images/maptime.gif' alt='maptime logo gif' width='350px'/>";
    ;
    ;    // specify popup options
    ;    var customOptions =
    ;        {
    ;        'maxWidth': '30',
    ;        'className' : 'custom'
    ;        }
    ;
    ;    // create marker object, pass custom icon as option, pass content and options to popup, add to map
    ;    L.marker([43.64701, -79.39425], {icon: myIcon}).bindPopup(customPopup,customOptions).addTo(map);

    ;<br/><img src='http://joshuafrazier.info/images/maptime.gif' alt='maptime logo gif' width='350px'/>
    (doseq [{:keys [lat lng name] :as layer-spec} stops]
      (let [custom-popup name
            customOptions {:maxWidth  30
                           :className "custom"}
            marker (js/L.marker (clj->js [lat lng])
                                #js {:icon (js/L.icon #js {:iconUrl "anchor7.png"})})]
        (.bindPopup marker custom-popup customOptions)
        (.addTo marker leaflet)))

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; Basic popup
    ; The javascript equivalent
    ; L.popup({ elevation: 260.0 })
    ;       .setLatLng([37.7952, -122.4028])
    ;       .setContent("Transamerica Pyramid")
    ;       .addTo(map);

    #_(doseq [{:keys [lat lng name] :as layer-spec} stops]
        (let [popup (js/L.popup #js {:elevation    500
                                     :closeButton  false
                                     :closeOnClick false})]
          (.setLatLng popup (clj->js [lat lng]))
          (.setContent popup name)
          (.addTo popup leaflet)))

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; base json objects - the lines
    (doseq [{:keys [type url color linejoin weight] :as layer-spec} base-jsons]
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   weight
                                       :opacity  0.50}})))]
        (.addTo layer leaflet)))


    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ; the line to be highlighted
    (doseq [{:keys [type url color linejoin weight opacity] :as layer-spec} highlight-json]
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   weight
                                       :opacity  opacity}})))
            ]
        (.addTo layer leaflet)

        ))


    (.setView leaflet (clj->js [lat lng]) 12)

    ; map.setBounds(myGeojsonObject.getBounds());

    ))

;;;;;;;;;
;;;;;;;;;
;; The LeafletJS Reagent component.

(defn leaflet [mapspec]
  "A LeafletJS map component."
  (reagent/create-class
    {:get-initial-state     (fn [_] {:mapspec mapspec})
     :component-did-mount   leaflet-did-mount
     :component-will-update leaflet-will-update
     :render                leaflet-render}))


