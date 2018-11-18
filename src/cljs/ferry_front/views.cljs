(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.components.components :as components]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:div
      [:p (str "New booking: " @(re-frame/subscribe [::subs/new-booking]))]
      [:p (str "Atom: " @re-frame.db/app-db)]
      #_[components/lister ()]]
     [booking-form/booking-form]]))
