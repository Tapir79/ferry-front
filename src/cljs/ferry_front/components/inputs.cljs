(ns ferry-front.components.inputs
  (:require [reagent.core :as reagent :refer [atom]]))

(defn input-field [content name on-change-function]
  [:input {:name name :on-change on-change-function}])

;; Do this
(defn foo []  ;; A function
  (let [component-state (reagent/atom {:count 0})] ;; <-- not included in render function
    (fn []  ;; That returns a function  <-- render function is from here down
      [:div ;; That returns hiccup
       [:p "Current count is: " (get @component-state :count)]
       [:button {:on-click #(swap! component-state update-in [:count] inc)} "Increment"]])))
