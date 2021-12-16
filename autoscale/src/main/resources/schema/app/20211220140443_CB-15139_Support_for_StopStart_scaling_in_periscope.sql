-- // CB-15139 Support for StopStart scaling in periscope
-- Migration SQL that makes the change goes here.
ALTER TABLE "cluster" ADD COLUMN IF NOT EXISTS "stopstart_enabled" BOOLEAN;
ALTER TABLE "cluster" ALTER COLUMN "stopstart_enabled" SET DEFAULT true;
UPDATE "cluster" SET "stopstart_enabled" = true WHERE "stopstart_enabled" IS NULL;
ALTER TABLE "cluster" ALTER COLUMN "stopstart_enabled" SET NOT NULL;


-- //@UNDO
-- SQL to undo the change goes here.
ALTER TABLE "cluster" DROP COLUMN IF EXISTS "stopstart_enabled";

