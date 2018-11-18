(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom]]
            [ferry-front.events :as events]
            [ferry-front.components.inputs :as inputs]
            [ferry-front.components.buttons :as buttons]))

(enable-console-print!)

(defn booking-form []
  [:form

   [:h1 "Linje och rutt"]
   [:h2 "Välj linje du vill resa på"]
   [:h3 "Dropdown example"]


   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:line-from] (.-value (.-target %))]])}]

   [:br]
   [:p "Linje och rutt till"]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:line-to] (.-value (.-target %))]])}]

   [:button {:on-click #(re-frame/dispatch [::events/send-new-booking])} "Send"]

   #_#_#_#_#_#_[input-field {:content "linje och rutt från"
                        :name "linje från"
                        #_#_:on-change-function #(println (.-value (.-target %)))
                        :on-change-function #(re-frame/dispatch [::events/add-to-new-booking [[:line-from] (.-value (.-target %))]])}]
   [:br]
   [:p "Linje och rutt till"]
   [input-field {:content "linje och rutt till"
                        :name "linje till"
                        #_#_:on-change-function #(println (.-value (.-target %)))
                        :on-change-function #(re-frame/dispatch [::events/add-to-new-booking [[:line-to] (.-value (.-target %))]])}]

   [:br]
   [buttons/default {:on-click-function #(re-frame/dispatch [::events/send-new-booking])}]])


