(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   [ferry-front.views.header :refer [header]]
   [ferry-front.views.navigation :refer [main-navigation]]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.leaflet.basic-map :as basic-map]))

(defn main-panel []
    [:div {:class "flex flex-col m-auto max-w-5xl"}
     [header]
     [main-navigation]
     [:div
      [booking-form/booking-form]
      [:div {:class "flex justify-center"}
        [basic-map/home]]]])
