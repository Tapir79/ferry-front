(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   [ferry-front.views.header :refer [header]]
   [ferry-front.views.navigation :refer [main-navigation]]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.leaflet.basic-map :as basic-map]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div {:class "flex flex-col justify-center"}
     [header]
     [main-navigation]
     [:div
      [booking-form/booking-form]
      [:div {:class "w-full overflow-x-scroll"}
        [basic-map/home]]]]))
