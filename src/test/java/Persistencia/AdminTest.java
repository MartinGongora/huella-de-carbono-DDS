package Persistencia;

public class AdminTest {/*
  Usuario admin;
  Usuario agente;
  Persona personaAdmin;
  Persona personaAgente;

  RepositorioUsuarios repositorioUsuarios;
  RepositorioRoles repositorioRoles;

  RepositorioPersonas repositorioPersonas;

  @Before
  public void init(){
    repositorioUsuarios = new RepositorioUsuarios();
    repositorioRoles = new RepositorioRoles();
    repositorioPersonas = new RepositorioPersonas();

    personaAdmin = repositorioPersonas.buscar(1);
    admin = new Usuario();
    admin.setPersona(personaAdmin);
    admin.setNombreDeUsuario("mantico");
    admin.setContrasenia("Manti1212");
    admin.setNombre("Mariano");
    admin.setApellido("Antico");
    admin.setRol(repositorioRoles.buscar(1));

    repositorioUsuarios.guardar(admin);


    personaAgente = repositorioPersonas.buscar(2);
    agente = new Usuario();
    agente.setPersona(personaAgente);
    agente.setNombreDeUsuario("Marlboro");
    agente.setContrasenia("Marl1234");
    agente.setNombre("Marlboro");
    agente.setApellido("Man");
    agente.setRol(repositorioRoles.buscar(4));

    repositorioUsuarios.guardar(agente);
  }

  @Test
  public void testeo(){
    Assert.assertEquals(admin, repositorioUsuarios.buscarNombre("mantico"));
    Assert.assertEquals(agente, repositorioUsuarios.buscarNombre("Marlboro"));
  }*/
}
