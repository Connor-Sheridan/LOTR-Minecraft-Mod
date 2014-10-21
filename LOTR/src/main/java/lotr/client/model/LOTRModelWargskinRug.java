package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class LOTRModelWargskinRug extends ModelBase
{
	private LOTRModelWarg wargModel;
	
	public LOTRModelWargskinRug()
	{
		wargModel = new LOTRModelWarg();
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
        setRotationAngles();
        GL11.glTranslatef(0F, -0.3F, 0F);
        
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 0.4F, 1F);
        wargModel.body.render(f5);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0F, 0F);
        wargModel.tail.render(f5);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, -0.5F, 0.1F);
        wargModel.head.render(f5);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.3F, 0F, 0F);
        wargModel.leg1.render(f5);
        wargModel.leg3.render(f5);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(0.3F, 0F, 0F);
        wargModel.leg2.render(f5);
        wargModel.leg4.render(f5);
        GL11.glPopMatrix();
	}
	
	private void setRotationAngles()
	{
		wargModel.leg1.rotateAngleX = (float)Math.toRadians(30F);
		wargModel.leg1.rotateAngleZ = (float)Math.toRadians(90F);
		
		wargModel.leg2.rotateAngleX = (float)Math.toRadians(30F);
		wargModel.leg2.rotateAngleZ = (float)Math.toRadians(-90F);
		
		wargModel.leg3.rotateAngleX = (float)Math.toRadians(-20F);
		wargModel.leg3.rotateAngleZ = (float)Math.toRadians(90F);
		
		wargModel.leg4.rotateAngleX = (float)Math.toRadians(-20F);
		wargModel.leg4.rotateAngleZ = (float)Math.toRadians(-90F);
	}
}
