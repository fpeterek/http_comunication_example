#include <SFML/Network.hpp>
#include <iostream>

int main(int argc, const char * argv[]) {
    
    sf::Http http;
    http.setHost("127.0.0.1", 8000);
    
    
    sf::Http::Request request;
    request.setUri("/test=test");
    request.setMethod(sf::Http::Request::Method::Get);
    
    sf::Http::Response response = http.sendRequest(request);
    
    if (response.getStatus() != sf::Http::Response::Status::Ok) {
        std::cout << "Error: " << response.getStatus() << std::endl;
    }
    
    std::cout << "Response: \n" << response.getBody() << std::endl;
    
}
