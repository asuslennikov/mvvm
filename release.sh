# Encrypt private sign key
#openssl aes-256-cbc -salt -k "$SIGN_KEY" -in release.key -out release.enc
# Decrypt private sign key
rm -f release.key
openssl aes-256-cbc -k "$SIGN_KEY" -in release.enc -out release.key -d
# Publish to Bintray
./gradlew publishAllPublicationsToBintrayRepository
# Publish to MavenCentral
./gradlew :mvvm-api:initializeSonatypeStagingRepository :mvvm-domain:initializeSonatypeStagingRepository :mvvm-presentation:initializeSonatypeStagingRepository publishToSonatype closeRepository