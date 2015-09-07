resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
  url("http://dl.bintray.com/banno/oss"))(
    Resolver.ivyStylePatterns)

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

addSbtPlugin("com.banno" % "sbt-license-plugin" % "0.1.4")
