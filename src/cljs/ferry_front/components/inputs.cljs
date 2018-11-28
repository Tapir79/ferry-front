(ns ferry-front.components.inputs)

(defn input-field [name on-change-function]
  [:div
    [:input {:name name :type "text" :on-change on-change-function}]])



