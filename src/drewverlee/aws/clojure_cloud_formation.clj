(ns drewverlee.aws.clojure-cloud-formation
  (:require
   [hickory.core :as h]
   [clojure.string :as str]
   [clojure.data.json :as json]
   [camel-snake-kebab.core :as csk]))

(defn aws-resource-string->aws-resource-keyword
  [aws-resource-string]
  (let [ss (map csk/->kebab-case (str/split aws-resource-string #"::|\."))]
    (keyword
     (str
      (str/join "."  (butlast ss))
      "/"
      (last ss)))))

(def cloud-formation-resource-specification
  (-> "CloudFormationResourceSpecification.json"
      slurp
      ;;TODO consider making all CamelCase keywords into snake-case keywords
      (json/read-str :key-fn (fn [k] (if-not (str/includes? k "::")
                                       (-> k keyword csk/->kebab-case)
                                       (aws-resource-string->aws-resource-keyword k))))))

(comment
  (->> cloud-formation-resource-specification
       :resource-types
       :aws.iam/role
       :properties
       keys)
  ;; => (:description
  ;;     :path
  ;;     :tags
  ;;     :assume-role-policy-document
  ;;     :policies
  ;;     :managed-policy-arns
  ;;     :max-session-duration
  ;;     :role-name
  ;;     :permissions-boundary)

  (->> cloud-formation-resource-specification
       :resource-types
       :aws.iam/role
       :properties
       :role-name)
  ;; => {:documentation
  ;;     "http://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-resource-iam-role.html#cfn-iam-role-rolename",
  ;;     :primitive-type "String",
  ;;     :required false,
  ;;     :update-type "Immutable"}


  nil)
