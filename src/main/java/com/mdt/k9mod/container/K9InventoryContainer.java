package com.mdt.k9mod.container;

import com.mdt.k9mod.common.entities.K9Entity;
import com.mdt.k9mod.core.init.K9Containers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class K9InventoryContainer extends Container {

    private final IInventory container;
    public int battery;

    public K9InventoryContainer(int p_i50065_1_, PlayerInventory p_i50065_2_) {
        this(p_i50065_1_, p_i50065_2_, new Inventory(27), 0);
    }

    public int getBattery() {
        return this.battery;
    }

    public K9InventoryContainer(int p_i50066_1_, PlayerInventory p_i50066_2_, IInventory p_i50066_3_, int batteryLevel) {
        super(K9Containers.K9_CONTAINER.get(), p_i50066_1_);
        checkContainerSize(p_i50066_3_, 27);
        this.container = p_i50066_3_;
        this.battery = batteryLevel;
        System.out.println(batteryLevel);
        p_i50066_3_.startOpen(p_i50066_2_.player);
        int i = 3;
        int j = 9;

        for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new ShulkerBoxSlot(p_i50066_3_, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }

        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(p_i50066_2_, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }

        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(p_i50066_2_, j1, 8 + j1 * 18, 142));
        }
    }

    public boolean stillValid(PlayerEntity p_75145_1_) {
        return this.container.stillValid(p_75145_1_);
    }

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        this.container.stopOpen(p_75134_1_);
    }
}
