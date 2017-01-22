package org.bitcoin.market.bean;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by classic1999 on 14-4-17.
 */
public enum OrderStatus {
    none {
        @Override
        public String toShort() {
            return "n";
        }

        @Override
        public boolean isRunning() {
            return true;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public boolean isNotFinish() {
            return true;
        }
    },
    part {
        @Override
        public String toShort() {
            return "p";
        }

        @Override
        public boolean isRunning() {
            return true;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public boolean isNotFinish() {
            return true;
        }
    },
    complete {
        @Override
        public String toShort() {
            return "";
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isComplete() {
            return true;
        }

        @Override
        public boolean isNotFinish() {
            return false;
        }
    },
    cancelled {
        @Override
        public String toShort() {
            return super.name();
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return true;
        }

        @Override
        public boolean isComplete() {
            return true;
        }

        @Override
        public boolean isNotFinish() {
            return false;
        }
    },
    created {
        @Override
        public String toShort() {
            return "c";
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public boolean isNotFinish() {
            return true;
        }
    };

    public static Collection<OrderStatus> notComplete() {
        Collection<OrderStatus> collection = new ArrayList<OrderStatus>();
        collection.add(created);
        collection.add(none);
        collection.add(part);
        return collection;
    }

    public static Collection<OrderStatus> all() {
        Collection<OrderStatus> collection = new ArrayList<OrderStatus>();
        collection.add(created);
        collection.add(none);
        collection.add(part);
        collection.add(complete);
        collection.add(cancelled);

        return collection;
    }


    public static Collection<OrderStatus> running() {
        Collection<OrderStatus> collection = new ArrayList<OrderStatus>();
        collection.add(none);
        collection.add(part);
        return collection;
    }

    public static Collection<OrderStatus> complete() {
        Collection<OrderStatus> collection = new ArrayList<OrderStatus>();
        collection.add(complete);
        collection.add(cancelled);
        return collection;
    }

    public abstract String toShort();

    public abstract boolean isRunning();

    public abstract boolean isCancelled();

    public abstract boolean isComplete();

    public abstract boolean isNotFinish();
}
