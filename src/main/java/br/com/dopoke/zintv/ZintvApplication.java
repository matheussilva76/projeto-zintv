package br.com.dopoke.zintv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dopoke.zintv.principal.Principal;

@SpringBootApplication
public class ZintvApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ZintvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}

}
