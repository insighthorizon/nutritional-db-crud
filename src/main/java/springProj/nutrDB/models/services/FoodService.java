package springProj.nutrDB.models.services;

import org.springframework.data.domain.Page;
import springProj.nutrDB.models.dto.FoodDTO;

import java.util.List;


/**
 * This interface specifies a service which exposes operations on food entries (persistent data in the model layer)
 * to the controller layer.
 */
public interface FoodService {

    /**
     * Creates a new food entry.
     * @param foodDTO Data the new entry will be based on.
     */
    void create (FoodDTO foodDTO);

    /**
     * Get all food entries from the database.
     * @return List of all foods (DTO) found in the database.
     */
    List<FoodDTO> getAll();

    /**
     * Get sorted Page of food entries containing a given substring in their name.
     * @param pageNumber Number of the requested page relative to entire number of pages of the given size.
     * @param pageSize Size of the requested page.
     * @param searchedName Substring that must be contained in each food entry. Note: empty string is contained
     *                     in all names. So we can use this method even without searching.
     * @param sortAttribute Name of the food attribute (id, name, kcal, protein, fats) by which to sort the pages.
     *                      Note: First all pages are sorted, and only then the page with requested number is extracted.
     * @return Page of food entries (as DTOs) that was found based on the above parameters.
     */
    Page<FoodDTO> getPage(int pageNumber, int pageSize, String searchedName, String sortAttribute);

    /**
     * Get one food entry (as DTO) by its id.
     * @param foodId ID of the food entry we are trying to retrieve from the databse.
     * @return The food entry (as DTO) found.
     */
    FoodDTO getById(long foodId);

    /**
     * Edit food entry in the database. Which entry? The parameter DTO contains ID as well as the new version of the data.
     * @param food DTO representing the new version of the food entry.
     */
    void edit(FoodDTO food);

    /**
     * Delete food entry from the database by its ID.
     * @param foodId ID of the food we attempt to delete.
     */
    void remove(long foodId);

}
