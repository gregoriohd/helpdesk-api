package br.com.gregoriohd;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.gregoriohd.domain.Chamado;
import br.com.gregoriohd.domain.Cliente;
import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.enums.Perfil;
import br.com.gregoriohd.domain.enums.Prioridade;
import br.com.gregoriohd.domain.enums.Status;
import br.com.gregoriohd.repsository.ChamadoRepository;
import br.com.gregoriohd.repsository.ClienteRepository;
import br.com.gregoriohd.repsository.TecnicoRepository;

@SpringBootApplication
public class CursoDevFullApplication implements CommandLineRunner {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoDevFullApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Tecnico t1 = new Tecnico(null, "Gregorio Santos", "000.999.888-00", 
				"gregori@gregoriosantos.com.br", "123g456g");
		t1.addPerfil(Perfil.ADMIN);
		t1.addPerfil(Perfil.TECNICO);
		
		Cliente c1 = new Cliente(null, "Linus Torvalds", "705.117.440-13", "torvalds@mail.com", "123");
		
		Chamado ch = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, 
				"Chamado 01","Primeiro Chamado", 
				c1, t1);
		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));;
		chamadoRepository.saveAll(Arrays.asList(ch));;
	}

}
