JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 mvn clean package

cd distribution/target/distribution-2.2.3.3-bin


#java -jar starter-1.0.0.jar -algebraName 5d -i threespheres.clu  -generator de.gaalop.codegenGappIntrinsics.Plugin -optimizer de.gaalop.gapp.Plugin
#sudo /'~'/Dokuments/bin/riscv64-unknown-elf-gcc  threespheres.c -O2
#sudo /'~'/Dokuments/bin/qemu-riscv64 -cpu rv64,x-v=true,vlen=256,elen=64,vext_spec=v1.0 a.out


#java -jar starter-1.0.0.jar -algebraName 5d -i reflexion.clu  -generator de.gaalop.codegenGappIntrinsics.Plugin -optimizer de.gaalop.gapp.Plugin
#sudo /'~'/Dokuments/bin/riscv64-unknown-elf-gcc  reflexion.c -O2
#sudo /'~'/Dokuments/bin/qemu-riscv64 -cpu rv64,x-v=true,vlen=256,elen=64,vext_spec=v1.0 a.out
java -jar starter-1.0.0.jar

#java -jar starter-1.0.0.jar --cli -i threespheres.clu --gapp --cg de.gaalop.codegenGappIntrinsics.Plugin -m /usr/bin/maxima



cd ../../..
#kate distribution/target/distribution-2.2.3.3-bin/threespheres.c
#cat distribution/target/distribution-2.2.3.3-bin/reflexion.c





#kate distribution/target/distribution-2.2.3.3-bin/threespheres.c