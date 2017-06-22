credentials += Credentials("Sonatype Nexus Repository Manager",
                          "oss.sonatype.org",
                          sys.props.getOrElse("ossrhUsername", null),
                          sys.props.getOrElse("ossrhPassword", null))