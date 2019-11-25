mvn clean package
mv ./target/seq.war ./target/seq_war_exploded.war
docker build -t gameseq .
docker run -p 8080:8080 gameseq