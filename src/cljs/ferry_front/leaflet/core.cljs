(ns ferry-front.leaflet.core
  (:require [reagent.core :as reagent :refer [atom]]))

;;;;;;;;;
;; Define the React lifecycle callbacks to manage the LeafletJS
;; Javascript objects.

#_(declare update-leaflet-geometries)
(declare update-leaflet-json-layers)

(defn- leaflet-did-mount [this]
  "Initialize LeafletJS map for a newly mounted map component."
  (let [mapspec (:mapspec (reagent/state this))
        leaflet (js/L.map (:id mapspec))
        view (:view mapspec)
        zoom (:zoom mapspec)
        line-weight (:weight mapspec)
        ]

    #_(println "view" line-weight)
    (.setView leaflet (clj->js @view) @zoom)

    (doseq [{:keys [type url] :as layer-spec} (:layers mapspec)]
      #_(println "url" url)
      (println "mapspec:" mapspec)
      (let [layer (case type
                    :tile (js/L.tileLayer
                            url
                            (clj->js {:attribution (:attribution layer-spec)})
                            )
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
      #_(println "type" type)
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   line-weight
                                       :opacity  0.50}})))]
        ;;(.log js/console "L.tileLayer = " layer)
        (.addTo layer leaflet)))

    ;;(.log js/console "L.map = " leaflet)
    (reagent/set-state this {:leaflet        leaflet
                             :geometries-map {}
                             :jsons-map      {}})

    ;; Add callback for leaflet pos/zoom changes
    ;; watcher for pos/zoom atoms
    (.on leaflet "move" (fn [e]
                          (let [c (.getCenter leaflet)]
                            (reset! zoom (.getZoom leaflet))
                            (reset! view [(.-lat c) (.-lng c)]))))
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


(defn- leaflet-will-update [this old-state new-state]
  (update-leaflet-json-layers this old-state))

(defn- leaflet-render [this]
  (let [mapspec (-> this reagent/state :mapspec)]
    [:div {:id    (:id mapspec)
           :style {:width  (:width mapspec)
                   :height (:height mapspec)}}]))


(defn- update-leaflet-json-layers [this old-state]
  (let [{:keys [leaflet]} (reagent/state this)
        this-state (reagent/state this)
        mapspec (:mapspec this-state)
        base-jsons (:base-jsons mapspec)
        stops (:stops mapspec)
        old-highlight-json (:jsons mapspec)
        highlight-json (:jsons (second old-state))]

    #_#_#_#_leaflet (:leaflet (reagent/state this))

        previous-state (reagent/state old-state)
    #_(doseq [x old-state]
        (println "old:state x:" x))

    #_#_#_(println "base-jsons" base-jsons)
        (println "highlight-jsons" old-highlight-json)
        (println "highlight-json" highlight-json)


    #_(println "old-state" old-state)


    (doseq [removed old-highlight-json]
      ;;(.log js/console "Removed: " removed)
      #_(println "removed layer" removed)
      (.removeLayer leaflet removed))


    ; let's add the now highlighted json to state
    (reagent/set-state this {:jsons highlight-json})


    ;L.circle([37.786542, -122.386022], {
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
                                    :fillOpacity 0.9
                                    })]
        (.addTo layer leaflet)
        ))


    ;L.popup({ elevation: 260.0 })
    ;       .setLatLng([37.7952, -122.4028])
    ;       .setContent("Transamerica Pyramid")
    ;       .addTo(map);

    (doseq [{:keys [lat lng name] :as layer-spec} stops]
      (let [popup (js/L.popup #js {:elevation 260.0
                                   :closeButton false
                                   :closeOnClick false})]
        (.setLatLng popup (clj->js [lat lng]))
        (.setContent popup name)
        (.addTo popup leaflet)))

    (doseq [{:keys [type url color linejoin weight] :as layer-spec} base-jsons]
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   weight
                                       :opacity  0.50}}))

                  )]
        (.addTo layer leaflet)))


    (doseq [{:keys [type url color linejoin weight opacity] :as layer-spec} highlight-json]
      (println "color" color)
      (println "opacity" opacity)
      (let [layer (case type
                    :json (js/L.geoJson
                            (.parse js/JSON url)
                            (clj->js {:style
                                      {:color    color
                                       :linejoin linejoin
                                       :weight   weight
                                       :opacity  opacity}})))]
        ;;(.log js/console "L.tileLayer = " layer)
        #_(.bindPopUp (fn [layer] "test"))                  ;doesn't work
        (.addTo layer leaflet)))

    ))


;;;;;;;;;
;; The LeafletJS Reagent component.

(defn leaflet [mapspec]
  "A LeafletJS map component."
  (reagent/create-class
    {:get-initial-state     (fn [_] {:mapspec mapspec})
     :component-did-mount   leaflet-did-mount
     :component-will-update leaflet-will-update
     :render                leaflet-render}))


