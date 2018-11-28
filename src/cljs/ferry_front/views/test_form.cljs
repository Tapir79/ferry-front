(ns ferry-front.views.test-form
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom]]
            [ferry-front.events :as events]
            [ferry-front.subs :as subs]
            [ferry-front.components.inputs :as inputs]
            [ferry-front.components.buttons :as buttons]
            [ferry-front.components.lists :as lists]))

(enable-console-print!)

(defn test-list []
  (let [tests @(re-frame/subscribe [::subs/tests])]
    [lists/bullet-list :message tests]))


(defn test-form []
  [:form
   [:h3 "Textfield example"]
   [:br]
   [inputs/input-field "from" #(re-frame/dispatch [::events/add-to-new-test [[:message] (.-value (.-target %))]])]
   [:br]
   [buttons/default "Send a message" #(re-frame/dispatch [::events/save-test])]])
