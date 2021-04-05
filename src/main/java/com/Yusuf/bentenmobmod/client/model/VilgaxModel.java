// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


public class VilgaxModel extends EntityModel<Entity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer tentacle;
	private final ModelRenderer tentacle2;
	private final ModelRenderer tentacle3;
	private final ModelRenderer tentacle4;
	private final ModelRenderer tentacle5;
	private final ModelRenderer tentacle6;
	private final ModelRenderer tentacle7;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;

	public VilgaxModel() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -7.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F, false);
		body.setTextureOffset(80, 0).addBox(-6.0F, 0.0F, -6.5F, 12.0F, 7.0F, 1.0F, 0.1F, false);
		body.setTextureOffset(37, 57).addBox(6.0F, -2.0F, -6.5F, 1.0F, 12.0F, 1.0F, 0.1F, false);
		body.setTextureOffset(20, 57).addBox(-7.0F, -2.0F, -6.5F, 1.0F, 12.0F, 1.0F, 0.1F, false);
		body.setTextureOffset(33, 45).addBox(-9.0F, -2.0F, -6.5F, 2.0F, 5.0F, 1.0F, 0.1F, false);
		body.setTextureOffset(0, 42).addBox(7.0F, -2.0F, -6.5F, 2.0F, 5.0F, 1.0F, 0.1F, false);
		body.setTextureOffset(51, 16).addBox(-5.5F, 10.0F, -4.0F, 11.0F, 5.0F, 7.0F, 0.5F, false);
		body.setTextureOffset(31, 33).addBox(-6.5F, 11.0F, -5.0F, 13.0F, 3.0F, 9.0F, -0.1F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -10.0F, 0.0F);
		head.setTextureOffset(62, 60).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(0, 23).addBox(-5.0F, -9.0F, -5.5F, 10.0F, 9.0F, 10.0F, 0.0F, false);
		head.setTextureOffset(0, 45).addBox(-6.0F, -6.0F, -3.5F, 12.0F, 3.0F, 9.0F, 0.0F, false);

		tentacle = new ModelRenderer(this);
		tentacle.setRotationPoint(-2.5F, -1.0F, -4.0F);
		head.addChild(tentacle);
		setRotationAngle(tentacle, -0.2618F, 0.0F, 0.0F);
		tentacle.setTextureOffset(36, 26).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.1F, false);

		tentacle2 = new ModelRenderer(this);
		tentacle2.setRotationPoint(0.0F, 3.0F, -2.0F);
		head.addChild(tentacle2);
		tentacle2.setTextureOffset(30, 23).addBox(-2.0F, -4.0F, -4.0F, 1.0F, 3.0F, 4.0F, 0.1F, false);

		tentacle3 = new ModelRenderer(this);
		tentacle3.setRotationPoint(1.5F, -0.5F, -4.0F);
		head.addChild(tentacle3);
		setRotationAngle(tentacle3, -0.2182F, 0.0F, 0.0F);
		tentacle3.setTextureOffset(0, 0).addBox(-0.5F, -1.5F, -2.0F, 1.0F, 3.0F, 4.0F, 0.1F, false);

		tentacle4 = new ModelRenderer(this);
		tentacle4.setRotationPoint(2.5F, 0.0F, -4.0F);
		head.addChild(tentacle4);
		setRotationAngle(tentacle4, -0.0436F, 0.0F, 0.0F);
		tentacle4.setTextureOffset(42, 24).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.1F, false);

		tentacle5 = new ModelRenderer(this);
		tentacle5.setRotationPoint(-2.0F, 0.25F, -4.0F);
		head.addChild(tentacle5);
		setRotationAngle(tentacle5, -0.2182F, 0.0F, 0.0F);
		tentacle5.setTextureOffset(0, 23).addBox(1.0F, -1.25F, -2.0F, 1.0F, 3.0F, 4.0F, 0.1F, false);

		tentacle6 = new ModelRenderer(this);
		tentacle6.setRotationPoint(-2.5F, -1.0F, -4.0F);
		head.addChild(tentacle6);
		setRotationAngle(tentacle6, -0.2618F, 0.0F, 0.0F);
		tentacle6.setTextureOffset(36, 26).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.1F, false);

		tentacle7 = new ModelRenderer(this);
		tentacle7.setRotationPoint(-2.0F, 0.25F, -4.0F);
		head.addChild(tentacle7);
		tentacle7.setTextureOffset(42, 24).addBox(-2.0F, -1.25F, -2.0F, 1.0F, 2.0F, 4.0F, 0.1F, false);

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-11.0F, -7.0F, 0.0F);
		right_arm.setTextureOffset(0, 57).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 23.0F, 6.0F, 0.0F, false);
		right_arm.setTextureOffset(58, 0).addBox(-3.0F, -3.0F, -4.0F, 7.0F, 7.0F, 8.0F, 0.0F, false);
		right_arm.setTextureOffset(66, 28).addBox(-2.5F, 16.0F, -3.5F, 5.0F, 7.0F, 7.0F, 0.0F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(11.0F, -7.0F, 0.0F);
		left_arm.setTextureOffset(42, 45).addBox(-2.0F, -0.5F, -3.0F, 4.0F, 23.0F, 6.0F, 0.0F, false);
		left_arm.setTextureOffset(62, 45).addBox(-4.0F, -3.0F, -4.0F, 7.0F, 7.0F, 8.0F, 0.0F, false);
		left_arm.setTextureOffset(20, 67).addBox(-2.5F, 16.0F, -3.5F, 5.0F, 7.0F, 7.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-3.0F, 8.0F, 0.0F);
		right_leg.setTextureOffset(44, 74).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 16.0F, 4.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(3.0F, 8.0F, 0.0F);
		left_leg.setTextureOffset(62, 69).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 16.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}