/**
 *
 * @author elio.adriao
 * @link https://github.com/elioadriao/Maciota
 * 
 **/
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestConta {
	
	/** CRIACAO DAS CONTAS A SEREM USADAS NOS TESTES **/
	static ContaPoupanca ContaP;
	static ContaCorrente ContaC;

	/** INICIALIZACAO DAS CONTAS ANTES DE TODA EXECUCAO DE TESTE **/
	@BeforeEach
	void setUp() throws Exception {
		ContaP = new ContaPoupanca("11111", "1234", 1000.0, "0");
		ContaC = new ContaCorrente("11111", "1234", 1000.0, "1");
	}
	/*****************************************/
	/** TESTES DE FUNCIONALIDADE DAS CONTAS **/
	/*****************************************/
	
	/** TESTE DE AUTENTICACAO DAS CONTAS **/
	@Test
	void testAuth() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals(true, ContaP.auth("11111", "1234"));
		assertEquals(false, ContaP.auth("01111", "1034"));
		assertEquals(false, ContaP.auth("0", "1234"));
		assertEquals(false, ContaP.auth("11111", "0"));
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals(true, ContaC.auth("11111", "1234"));
		assertEquals(false, ContaC.auth("01111", "1034"));
		assertEquals(false, ContaC.auth("0", "1234"));
		assertEquals(false, ContaC.auth("11111", "0"));
	}
	
	/** TESTE DE GET/SET SALDO DAS CONTAS **/
	@Test
	void testSaldo() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals(1000.0f, ContaP.getSaldo());
		ContaP.setSaldo(-20.0);
		assertEquals(-20.0f, ContaP.getSaldo());
		ContaP.setSaldo(0.0);
		assertEquals(0.0f, ContaP.getSaldo());
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals(1000.0f, ContaC.getSaldo());
		ContaC.setSaldo(-20.0);
		assertEquals(-20.0f, ContaC.getSaldo());
		ContaC.setSaldo(0.0);
		assertEquals(0.0f, ContaC.getSaldo());
	}
	
	/** TESTE DE GET/SET CODIGO DAS CONTAS **/
	@Test
	void testCodigo() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals("11111", ContaP.getCodigo());
		ContaP.setCodigo("");
		assertEquals("", ContaP.getCodigo());
		ContaP.setCodigo("teste");
		assertEquals("teste", ContaP.getCodigo());
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals("11111", ContaC.getCodigo());
		ContaC.setCodigo("");
		assertEquals("", ContaC.getCodigo());
		ContaC.setCodigo("teste");
		assertEquals("teste", ContaC.getCodigo());
	}
	
	/** TESTE DE GET/SET SENHA DAS CONTAS **/
	@Test
	void testSenha() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals("1234", ContaP.getSenha());
		ContaP.setSenha("");
		assertEquals("", ContaP.getSenha());
		ContaP.setSenha("teste");
		assertEquals("teste", ContaP.getSenha());
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals("1234", ContaC.getSenha());
		ContaC.setSenha("");
		assertEquals("", ContaC.getSenha());
		ContaC.setSenha("teste");
		assertEquals("teste", ContaC.getSenha());
	}
	
	/** TESTE DE GET/SET VARIACAO DAS CONTAS **/
	@Test
	void testVariacao() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals("Poupança", ContaP.getVariacao());
		ContaP.setVariacao("1");
		assertEquals("Corrente", ContaP.getVariacao());
		ContaP.setVariacao("teste");
		assertEquals("Poupança", ContaP.getVariacao());
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals("Corrente", ContaC.getVariacao());
		ContaC.setVariacao("");
		assertEquals("Poupança", ContaC.getVariacao());
		ContaC.setVariacao("teste");
		assertEquals("Poupança", ContaC.getVariacao());
	}
	
	/** TESTE DE CHECAR SAQUE DISPONIVEL NAS CONTAS **/
	@Test
	void testCheckSaldo() {
		/** TESTES DE CONTA POUPANCA **/
		assertEquals(true, ContaP.checkSaldo(1.0));
		assertEquals(false, ContaP.checkSaldo(1001.0));
		assertEquals(false, ContaP.checkSaldo(-1.0));
		
		/** TESTES DE CONTA CORRENTE **/
		assertEquals(true, ContaC.checkSaldo(1.0));
		assertEquals(false, ContaC.checkSaldo(1001.0));
		assertEquals(false, ContaC.checkSaldo(-1.0));
	}
	
	/** TESTE DE DEPOSITO DAS CONTAS **/
	@Test
	void testDoDeposito() {
		/** TESTES DE CONTA POUPANCA **/
		ContaP.doDeposito(11.0);
		assertEquals(1011.0f, ContaP.getSaldo());
		ContaP.doDeposito(-1.0);
		assertEquals(1010.0f, ContaP.getSaldo());
		ContaP.doDeposito(990.0);
		assertEquals(2000.0f, ContaP.getSaldo());
		
		/** TESTES DE CONTA CORRENTE **/
		ContaC.doDeposito(11.0);
		assertEquals(1011.0f, ContaC.getSaldo());
		ContaC.doDeposito(-1.0);
		assertEquals(1010.0f, ContaC.getSaldo());
		ContaC.doDeposito(990.0);
		assertEquals(2000.0f, ContaC.getSaldo());
	}
	
	/******************************************/
	/** TESTES ESPECIFICOS DA CONTA POUPANCA **/
	/******************************************/
	
	/** TESTE DE SAQUE NA CONTA POUPANCA **/
	@Test
	void testDoSaquePoupanca() {
		/* Testa Saque Normal */
		ContaP.doSaque(10.0);
		assertEquals(990.0f, ContaP.getSaldo(), 0.05);
		
		/* Testa Saque Invalido */
		try {
			ContaP.doSaque(-1.0);
			ContaP.doSaque(1001.0);
			fail();
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
	}
	
	/** TESTE DE SALDO NA CONTA POUPANCA **/
	@Test
	void testDoSaldoPoupanca() {
		assertEquals(1000.0f, ContaP.doSaldo());
		ContaP.doSaque(200.0);
		assertEquals(800.0f, ContaP.doSaldo());
		ContaP.doSaque(22.0);
		assertEquals(778.0f, ContaP.doSaldo());
	}
	
	/******************************************/
	/** TESTES ESPECIFICOS DA CONTA CORRENTE **/
	/******************************************/
	
	/** TESTE DE SAQUE NA CONTA CORRENTE **/
	@Test
	void testDoSaqueCorrente() {
		/* Testa Saque Normal */
		ContaC.doSaque(10.0);
		assertEquals(989.5f, ContaC.getSaldo(), 0.05);
		
		/* Testa Saque Invalido */
		try {
			ContaC.doSaque(-1.0);
			ContaC.doSaque(1000.0);
			fail();
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
	}
	
	/** TESTE DE SALDO NA CONTA CORRENTE **/
	@Test
	void testDoSaldoCorrente() {
		assertEquals(999.9f, ContaC.doSaldo(),0.05);
		assertEquals(999.8f, ContaC.doSaldo(),0.05);
		assertEquals(999.7f, ContaC.doSaldo(),0.05);
	}
}