(ns ferry-front.components.inputs)

(defn input-field [name on-change-function]
  [:div {:class "ui input focus"}
    [:input {:name name :type "text" :on-change on-change-function}]])



