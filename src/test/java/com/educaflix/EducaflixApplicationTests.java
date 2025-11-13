package com.educaflix;

import com.educaflix.util.CpfValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suíte de testes automatizados do sistema EducaFlix.
 * Contém 15 testes organizados em 2 categorias principais.
 *
 */
@SpringBootTest
@DisplayName("Testes do Sistema EducaFlix")
class EducaflixApplicationTests {

	@Test
	@DisplayName("Deve carregar o contexto da aplicação Spring Boot")
	void contextLoads() {
	}

	@Nested
	@DisplayName("Testes do Validador de CPF")
	class CpfValidatorTests {

		@Test
		@DisplayName("Deve validar CPF válido com pontos e hífen (123.456.789-09)")
		void deveValidarCpfValidoComFormatacao() {
			String cpf = "123.456.789-09";
			assertTrue(CpfValidator.isValid(cpf), "CPF com formatação deveria ser válido");
		}

		@Test
		@DisplayName("Deve validar CPF válido sem formatação (12345678909)")
		void deveValidarCpfValidoSemFormatacao() {
			String cpf = "12345678909";
			assertTrue(CpfValidator.isValid(cpf), "CPF sem formatação deveria ser válido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com dígitos repetidos (11111111111)")
		void deveRejeitarCpfComDigitosRepetidos() {
			String cpf = "11111111111";
			assertFalse(CpfValidator.isValid(cpf), "CPF com dígitos repetidos deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com todos os dígitos zero (00000000000)")
		void deveRejeitarCpfComZeros() {
			String cpf = "00000000000";
			assertFalse(CpfValidator.isValid(cpf), "CPF com zeros deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com dígito verificador inválido (12345678900)")
		void deveRejeitarCpfComDigitoVerificadorInvalido() {
			String cpf = "12345678900";
			assertFalse(CpfValidator.isValid(cpf), "CPF com dígito verificador incorreto deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com menos de 11 dígitos (123456789)")
		void deveRejeitarCpfComMenosDigitos() {
			String cpf = "123456789";
			assertFalse(CpfValidator.isValid(cpf), "CPF com menos de 11 dígitos deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com mais de 11 dígitos (123456789012)")
		void deveRejeitarCpfComMaisDigitos() {
			String cpf = "123456789012";
			assertFalse(CpfValidator.isValid(cpf), "CPF com mais de 11 dígitos deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF nulo")
		void deveRejeitarCpfNulo() {
			assertFalse(CpfValidator.isValid(null), "CPF nulo deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF vazio")
		void deveRejeitarCpfVazio() {
			assertFalse(CpfValidator.isValid(""), "CPF vazio deveria ser inválido");
		}

		@Test
		@DisplayName("Deve rejeitar CPF com apenas espaços em branco")
		void deveRejeitarCpfComEspacos() {
			assertFalse(CpfValidator.isValid("   "), "CPF com espaços deveria ser inválido");
		}

		@Test
		@DisplayName("Deve formatar CPF corretamente (12345678909 → 123.456.789-09)")
		void deveFormatarCpfCorretamente() {
			String cpf = "12345678909";
			String esperado = "123.456.789-09";
			assertEquals(esperado, CpfValidator.format(cpf), "CPF deveria ser formatado corretamente");
		}

		@Test
		@DisplayName("Deve manter formato ao formatar CPF já formatado")
		void deveManterCpfJaFormatado() {
			String cpf = "123.456.789-09";
			String resultado = CpfValidator.format(cpf);
			assertTrue(resultado.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"),
					"CPF já formatado deveria manter o padrão XXX.XXX.XXX-XX");
		}

		@Test
		@DisplayName("Deve retornar null ao formatar CPF nulo")
		void deveRetornarNullAoFormatarCpfNulo() {
			assertNull(CpfValidator.format(null), "Formatar CPF nulo deveria retornar null");
		}

		@Test
		@DisplayName("Deve retornar CPF original ao formatar CPF inválido (menos de 11 dígitos)")
		void deveRetornarOriginalAoFormatarCpfInvalido() {
			String cpf = "123";
			assertEquals(cpf, CpfValidator.format(cpf),
					"CPF inválido deveria retornar a string original");
		}
	}
}
