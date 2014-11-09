package lotr.client.model;

import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBalrog extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer neck;
	public ModelRenderer head;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer tail;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	
	public int heldItemRight;
	
	public LOTRModelBalrog()
	{
		this(0F);
	}
	
	public LOTRModelBalrog(float f)
	{
		textureWidth = 128;
		textureHeight = 256;
		
		body = new ModelRenderer(this, 0, 38);
		body.setRotationPoint(0F, 7F, 3F);
		body.addBox(-8F, -15F, -6F, 16, 18, 12, f);
		body.setTextureOffset(0, 207);
		body.addBox(-9F, -6.5F, -7F, 7, 1, 14, f);
		body.addBox(-9F, -9.5F, -7F, 7, 1, 14, f);
		body.addBox(-9F, -12.5F, -7F, 7, 1, 14, f);
		body.mirror = true;
		body.addBox(2F, -6.5F, -7F, 7, 1, 14, f);
		body.addBox(2F, -9.5F, -7F, 7, 1, 14, f);
		body.addBox(2F, -12.5F, -7F, 7, 1, 14, f);
		body.mirror = false;
		body.setTextureOffset(0, 0).addBox(-9F, -29F, -7F, 18, 14, 15, f);
		body.setTextureOffset(81, 163).addBox(-2F, -21F, 5.5F, 4, 25, 2, f);
		
		neck = new ModelRenderer(this, 76, 0);
		neck.setRotationPoint(0F, -25F, -3F);
		neck.addBox(-6F, -5F, -10F, 12, 12, 14, f);
		body.addChild(neck);
		
		head = new ModelRenderer(this, 92, 48);
		head.setRotationPoint(0F, 0F, -10F);
		head.addBox(-4F, -6F, -6F, 8, 10, 7, f);
		head.setTextureOffset(57, 58).addBox(-6F, -7F, -4F, 12, 4, 4, f);
		head.rotateAngleX = (float)Math.toRadians(10D);
		neck.addChild(head);
		
		ModelRenderer rightHorn1 = new ModelRenderer(this, 57, 47);
		rightHorn1.setRotationPoint(-6F, -5F, -2F);
		rightHorn1.addBox(-7F, -1.5F, -1.5F, 8, 3, 3, f);
		rightHorn1.rotateAngleY = (float)Math.toRadians(-35D);
		head.addChild(rightHorn1);
		
		ModelRenderer rightHorn2 = new ModelRenderer(this, 57, 35);
		rightHorn2.setRotationPoint(-7F, 0F, 0F);
		rightHorn2.addBox(-1F, -1F, -6F, 2, 2, 6, f);
		rightHorn2.rotateAngleY = (float)Math.toRadians(45D);
		rightHorn1.addChild(rightHorn2);
		
		ModelRenderer leftHorn1 = new ModelRenderer(this, 57, 47);
		leftHorn1.setRotationPoint(6F, -5F, -2F);
		leftHorn1.mirror = true;
		leftHorn1.addBox(-1F, -1.5F, -1.5F, 8, 3, 3, f);
		leftHorn1.rotateAngleY = (float)Math.toRadians(35D);
		head.addChild(leftHorn1);
		
		ModelRenderer leftHorn2 = new ModelRenderer(this, 57, 35);
		leftHorn2.setRotationPoint(7F, 0F, 0F);
		leftHorn2.mirror = true;
		leftHorn2.addBox(-1F, -1F, -6F, 2, 2, 6, f);
		leftHorn2.rotateAngleY = (float)Math.toRadians(-45D);
		leftHorn1.addChild(leftHorn2);
		
		rightArm = new ModelRenderer(this, 59, 136);
		rightArm.setRotationPoint(-9F, -25F, 0F);
		rightArm.addBox(-7F, -2F, -4F, 7, 10, 8, f);
		rightArm.setTextureOffset(93, 136).addBox(-6.5F, 8F, -3F, 6, 16, 6, f);
		body.addChild(rightArm);
		
		leftArm = new ModelRenderer(this, 59, 136);
		leftArm.setRotationPoint(9F, -25F, 0F);
		leftArm.mirror = true;
		leftArm.addBox(0F, -2F, -4F, 7, 10, 8, f);
		leftArm.setTextureOffset(93, 136).addBox(0.5F, 8F, -3F, 6, 16, 6, f);
		body.addChild(leftArm);
		
		rightLeg = new ModelRenderer(this, 46, 230);
		rightLeg.setRotationPoint(-6F, 6F, 3F);
		rightLeg.addBox(-7F, -2F, -4F, 7, 9, 8, f);
		rightLeg.setTextureOffset(46, 208).addBox(-6.5F, 2F, 4F, 6, 13, 5, f);
		
		ModelRenderer rightFoot = new ModelRenderer(this, 0, 243);
		rightFoot.setRotationPoint(0F, 0F, 0F);
		rightFoot.addBox(-7F, 15F, -6F, 7, 3, 9, f);
		rightFoot.rotateAngleX = (float)Math.toRadians(25D);
		rightLeg.addChild(rightFoot);
		
		leftLeg = new ModelRenderer(this, 46, 230);
		leftLeg.setRotationPoint(6F, 6F, 3F);
		leftLeg.mirror = true;
		leftLeg.addBox(0F, -2F, -4F, 7, 9, 8, f);
		leftLeg.setTextureOffset(46, 208).addBox(0.5F, 2F, 4F, 6, 13, 5, f);
		
		ModelRenderer leftFoot = new ModelRenderer(this, 0, 243);
		leftFoot.setRotationPoint(0F, 0F, 0F);
		leftFoot.mirror = true;
		leftFoot.addBox(0F, 15F, -6F, 7, 3, 9, f);
		leftFoot.rotateAngleX = (float)Math.toRadians(25D);
		leftLeg.addChild(leftFoot);
		
		tail = new ModelRenderer(this, 79, 200);
		tail.setRotationPoint(0F, -3F, 3F);
		tail.addBox(-3.5F, -3F, 2F, 7, 7, 10, f);
		tail.setTextureOffset(80, 225).addBox(-2.5F, -2.5F, 11F, 5, 5, 14, f);
		tail.setTextureOffset(96, 175).addBox(-1.5F, -2F, 24F, 3, 3, 12, f);
		body.addChild(tail);
		
		rightWing = new ModelRenderer(this, 0, 137);
		rightWing.setRotationPoint(-6F, -27F, 4F);
		rightWing.addBox(-1.5F, -1.5F, 0F, 3, 3, 25, f);
		rightWing.setTextureOffset(0, 167).addBox(-1F, -2F, 25F, 2, 24, 2, f);
		rightWing.setTextureOffset(0, 30).addBox(-0.5F, -7F, 25.5F, 1, 5, 1, f);
		rightWing.setTextureOffset(0, 69).addBox(0F, 0F, 0F, 0, 35, 25, f);
		body.addChild(rightWing);
		
		leftWing = new ModelRenderer(this, 0, 137);
		leftWing.setRotationPoint(6F, -27F, 4F);
		leftWing.mirror = true;
		leftWing.addBox(-1.5F, -1.5F, 0F, 3, 3, 25, f);
		leftWing.setTextureOffset(0, 167).addBox(-1F, -2F, 25F, 2, 24, 2, f);
		leftWing.setTextureOffset(0, 30).addBox(-0.5F, -7F, 25.5F, 1, 5, 1, f);
		leftWing.setTextureOffset(0, 69).addBox(0F, 0F, 0F, 0, 35, 25, f);
		body.addChild(leftWing);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        body.render(f5);
        rightLeg.render(f5);
        leftLeg.render(f5);
	}
	
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
		
		neck.rotateAngleX = (float)Math.toRadians(-10D);
		neck.rotateAngleY = 0F;
		neck.rotateAngleX += f4 / (float)Math.toDegrees(1D);
		neck.rotateAngleY += f3 / (float)Math.toDegrees(1D);
		
		body.rotateAngleX = (float)Math.toRadians(10D);
		body.rotateAngleX += MathHelper.cos(f2 * 0.03F) * 0.15F;

		rightArm.rotateAngleX = 0F;
        leftArm.rotateAngleX = 0F;
        rightArm.rotateAngleZ = 0F;
        leftArm.rotateAngleZ = 0F;
		
        rightArm.rotateAngleX += MathHelper.cos(f * 0.4F + (float)Math.PI) * 0.8F * f1;
        leftArm.rotateAngleX += MathHelper.cos(f * 0.4F) * 0.8F * f1;
        
        rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        
        if (onGround > -9990F)
        {
            float f6 = onGround;
            rightArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleX += body.rotateAngleY;
            f6 = 1F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1F - f6;
            float f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
            rightArm.rotateAngleX = (float)((double)rightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            rightArm.rotateAngleY += body.rotateAngleY * 2F;
            rightArm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * -0.4F;
        }
        
        if (heldItemRight != 0)
        {
            rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)heldItemRight;
        }
        
        rightLeg.rotateAngleX = (float)Math.toRadians(-25D);
        leftLeg.rotateAngleX = (float)Math.toRadians(-25D);

		rightLeg.rotateAngleX += MathHelper.sin(f * 0.4F) * 1.2F * f1;
		leftLeg.rotateAngleX += MathHelper.sin(f * 0.4F + (float)Math.PI) * 1.2F * f1;
		
		rightWing.rotateAngleX = (float)Math.toRadians(40D);
		leftWing.rotateAngleX = (float)Math.toRadians(40D);
		rightWing.rotateAngleY = (float)Math.toRadians(-40D);
		leftWing.rotateAngleY = (float)Math.toRadians(40D);
		
		rightWing.rotateAngleY += MathHelper.cos(f2 * 0.04F) * 0.5F;
		leftWing.rotateAngleY -= MathHelper.cos(f2 * 0.04F) * 0.5F;
		
		tail.rotateAngleX = (float)Math.toRadians(-40D);
		tail.rotateAngleY = 0F;
		tail.rotateAngleY += MathHelper.cos(f2 * 0.05F) * 0.15F;
		tail.rotateAngleY += MathHelper.sin(f * 0.1F) * 0.6F * f1;
	}
	
	public void setFireModel()
	{
		rightWing.showModel = false;
		leftWing.showModel = false;
	}
}
