(ns ferry-front.views
  (:require
   [ferry-front.views.header :refer [header]]
   [ferry-front.views.navigation :refer [main-navigation]]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.leaflet.basic-map :as basic-map]))

(defn main-panel []
    (println "Main view rendering")
    [:div {:class "flex flex-col m-auto max-w-5xl"}
     [header]
     [main-navigation]
     [:div
      [booking-form/booking-form]
      [:div {:class "w-full overflow-x-scroll"}
        [basic-map/home]]]])
