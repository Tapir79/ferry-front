(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   ;[ferry-front.views.test-form :as test-form]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.leaflet.basic-map :as basic-map]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:div
      [booking-form/booking-form]
      [basic-map/home]]]))
