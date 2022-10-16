CREATE TABLE stock_data (
      id BIGSERIAL PRIMARY KEY,
      ticker VARCHAR(100),
      quarter SMALLINT,
      date DATE,
      open DECIMAL,
      close DECIMAL,
      high DECIMAL,
      low DECIMAL,
      volume BIGINT,
      percent_change_price DECIMAL,
      previous_week_volume BIGINT,
      percent_change_volume_over_last_week DECIMAL,
      next_week_open DECIMAL,
      next_week_close DECIMAL,
      percent_change_next_week_price DECIMAL,
      days_to_next_dividend SMALLINT,
      percent_return_next_dividend DECIMAL
);