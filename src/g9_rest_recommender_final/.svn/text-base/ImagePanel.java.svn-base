/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g9_rest_recommender_final;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Christian
 */
public class ImagePanel extends JPanel {
     /*the default image to use*/
     String imageFile = "images/middle.GIF";

     public ImagePanel(){
        super();
     }

     public ImagePanel(String image){
         super();
         this.imageFile = image;
     }

     public ImagePanel(LayoutManager layout){
        super(layout);
     }

     public void paintComponent(Graphics g){
         /*create image icon to get image*/
         ImageIcon imageicon = new ImageIcon(getClass().getResource(imageFile));
         Image image = imageicon.getImage();

         /*Draw image on the panel*/
         super.paintComponent(g);
         if (image != null)
         g.drawImage(image, 0, 0, getWidth(), getHeight(), this);   
     }
}
