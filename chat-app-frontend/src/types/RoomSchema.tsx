import { z } from 'zod'

export const OwnerSchema = z.object({
    username: z.string(),
});

export const RoomItemResponseSchema = z.object({
    id: z.number(),
    name: z.string(),
    // users: z.array(z.string()),
    owner: OwnerSchema,
    description: z.string(),
})

export const RoomsResponseSchema = z.object({
    rooms: z.array(RoomItemResponseSchema)
});

export type RoomsDto = z.infer<typeof RoomsResponseSchema>;
export type RoomItemDto = z.infer<typeof RoomItemResponseSchema>;