package util;

import java.util.regex.Pattern;


// Futuramente para uso
public class Validacoes {

    public static boolean validarCpf(String cpf) {
        //
        String regex = "^[0-9]{11}$";
        return cpf != null && cpf.matches(regex);
    }

    public static boolean validarTelefone(String telefone) {
        String regex = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        return telefone != null && telefone.matches(regex);
    }

    public static boolean validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(regex);
    }

    public static boolean validarIdade(int idade) {
        return idade > 0 && idade < 120;
    }

    public static boolean validarCampoObrigatorio(String campo) {
        return campo != null && !campo.trim().isEmpty();
    }

    public static boolean validarPassaporte(String passaporte) {
        return passaporte != null && passaporte.matches("^[0-9]{9}$");
    }
}
