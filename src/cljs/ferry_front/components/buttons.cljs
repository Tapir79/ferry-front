(ns ferry-front.components.buttons)

(defn default [{:keys [content on-click-function]}]
  [:button {:on-click on-click-function} "Send"])