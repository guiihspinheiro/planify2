//Nome do pacote padrão
package br.com.grupotdb.grupotdb.Eventos;

//Impots de bibliotecas necessárias para a interface
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Uma interface é responsensavel por definir os métodos que terão uma classe. 
 * Nesse caso ela está extendendo do JPaRepository oque diz que essa interface possuí métodos de banco de dados.
 */
public interface EventoRepository extends JpaRepository<EventoModel, UUID> {
    EventoModel findBynomedoevento(String nomedoevento);
}