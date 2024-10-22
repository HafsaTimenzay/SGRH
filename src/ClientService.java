
public Client updateClient(Long id, Client clientDetails) {
// Trouver le client existant
Client existingClient = clientRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Client non trouvé avec id: " + id));
// Mettre à jour les informations du client
existingClient.setNom(clientDetails.getNom());
existingClient.setEmail(clientDetails.getEmail());
// Sauvegarder et retourner le client mis à jour
return clientRepository.save(existingClient);
}

public void delete(Long id) {
    clientRepository.deleteById(id);
}

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // public Client save(Client client) {
    // return clientRepository.save(client);
    // }
    // A ajouter apres les CRUDS
    public Client save(Client client) {
        // Vérifier si un client avec le même email existe déjà
        Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            throw new IllegalArgumentException("Un client avec cet email existe déjà.");
        }
        return clientRepository.save(client);
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

}