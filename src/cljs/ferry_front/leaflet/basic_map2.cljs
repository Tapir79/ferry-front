(ns ferry-front.leaflet.basic-map2)

(defn map2 []
  [leaflet {:id "kartta"
            :width "100%" :height "300px" ;; set width/height as CSS units
            :view view-position ;; map center position
            :zoom zoom-level ;; map zoom level

            ;; The actual map data (tile layers from OpenStreetMap), also supported is
            ;; :wms type
            :layers [{:type :tile
                      :url "http://{s}.tile.osm.org/{z}/{x}/{y}.png"
                      :attribution "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors"}]

            ;; Geometry shapes to draw to the map
            :geometries geometries

            ;; Add handler for map clicks
            :on-click #(when @drawing
                         ;; if drawing, add point to polyline
                         (swap! geometries
                                (fn [geometries]
                                  (let [pos (dec (count geometries))]
                                    (assoc geometries pos
                                                      {:type :line
                                                       :coordinates (conj (:coordinates (nth geometries pos))
                                                                          %)})))))}
   ]
  )

