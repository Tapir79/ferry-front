(ns ferry-front.views
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.subs :as subs]
   [ferry-front.views.booking-form :as booking-form]
   [ferry-front.components.components :as components]
   [ferry-front.views.test-form :as test-form]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:div
      [test-form/test-form]
      [:br]
      [test-form/test-list]]]))
