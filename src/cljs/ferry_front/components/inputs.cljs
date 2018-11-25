(ns ferry-front.components.inputs)


(defn input-field [name on-change-function]
  [:input {:name name :on-change on-change-function}])


