/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.u8t.urlbooster.bean;

import java.util.ArrayList;


public final class URLList extends ArrayList<WeightedURL> {

    public static enum URL_ID {
        CITIZEN, INDUSTRY, TRANSPORT, SECURITY, ENVIRONMENT, BUSINESS;
    }
    
    private static final long serialVersionUID = 1L;
    
    private int totalWeight = 0; 
    
    public URLList(URLList urlList) {
        for (WeightedURL url : urlList)
            add(url);
    }
    
    public URLList() {
        super();
    }
    
    public final int getTotalWeight() {
        return totalWeight;
    }
    
    /* 
     * Méthode renvoyant une URL en fonction d'un poids donné, qui doit être
     * compris entre 0 et le poids total de la liste.
     */
    public final WeightedURL getURLByWeight(final double weight) {
        int iterativeWeight = 0;
    
        if (0 <= weight && weight <= totalWeight) {
            for (WeightedURL url: this) {
                if (iterativeWeight < weight && weight < iterativeWeight + url.getWeight())
                    return url;
                
                iterativeWeight += url.getWeight();
            }
        }
        
        // Par défaut, nous retournons la première URL de la liste
        return this.get(0);
    }
    
    /* 
     * Méthode renvoyant une URL en fonction de son identifiant
     */
    public final WeightedURL getURLById(final URL_ID id) {
        for (WeightedURL url: this) {
            if (url.getId() == id) 
                return url;
        }
        
        // Par défaut, nous retournons la première URL de la liste
        return this.get(0);
    }
    
    // Override de la méthode add(E o) pour gestion du poids total
    public boolean add(WeightedURL url) {
        totalWeight += url.getWeight();
        return super.add(url);
    }
    
    // Override de la méthode remove(E o) pour gestion du poids total
    public boolean remove(WeightedURL url) {
        totalWeight -= url.getWeight();
        return super.remove(url);
    }
}
