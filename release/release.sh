# Encrypt private sign key
#openssl aes-256-cbc -salt -k "$SIGN_KEY" -md sha256 -in release/release.key -out release/release.enc
# Decrypt private sign key
rm -f release/release.key &&
openssl aes-256-cbc -k "$SIGN_KEY" -md sha256 -in release/release.enc -out release/release.key -d &&
# Publish to Bintray
./gradlew publishAllPublicationsToBintrayRepository &&
# Publish to MavenCentral
./gradlew publishToSonatype closeAndReleaseRepository &&
# Apply release tag
./gradlew applyReleaseTag &&
# Publish to Github
./gradlew githubRelease