package carpetstairsaddition.mixin;

import carpetstairsaddition.config.CSASettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class PlayerHeadCraftingMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!CSASettings.playerHeadCrafting) {
            return;
        }

        ItemEntity self = (ItemEntity) (Object) this;
        ItemStack stack = self.getItem();

        // 检查是否是成书
        if (stack.getItem() != Items.WRITTEN_BOOK) {
            return;
        }

        // 只在服务端执行
        if (self.level().isClientSide()) {
            return;
        }

        ServerLevel world = (ServerLevel) self.level();

        // 获取成书的标题（玩家名称）
        String playerName = getBookTitle(stack);
        if (playerName == null || playerName.isEmpty()) {
            return;
        }

        // 查找附近的凋零骷髅头颅
        AABB searchBox = self.getBoundingBox().inflate(1.0);
        for (ItemEntity nearby : world.getEntitiesOfClass(ItemEntity.class, searchBox,
                item -> item != self && item.getItem().getItem() == Items.WITHER_SKELETON_SKULL)) {

            ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);

            // 1.20.1 用 NBT 的 SkullOwner 保存头颅档案（1.20.5+ 才是 PROFILE 组件）
            CompoundTag skullOwner = new CompoundTag();
            GameProfile profile = new GameProfile(
                    UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                    playerName);
            NbtUtils.writeGameProfile(skullOwner, profile);
            playerHead.getOrCreateTag().put("SkullOwner", skullOwner);

            // 在原地生成玩家头颅掉落物
            ItemEntity headEntity = new ItemEntity(world, self.getX(), self.getY(), self.getZ(), playerHead);
            headEntity.setPickUpDelay(40);
            world.addFreshEntity(headEntity);

            // 移除成书和凋零骷髅头颅
            self.discard();
            nearby.discard();

            break; // 只处理一个
        }
    }

    private String getBookTitle(ItemStack bookStack) {
        CompoundTag tag = bookStack.getTag();
        if (tag != null && tag.contains(WrittenBookItem.TAG_TITLE, 8)) {
            return tag.getString(WrittenBookItem.TAG_TITLE);
        }
        return null;
    }
}