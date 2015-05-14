package com.oberasoftware.home.zwave;

import com.oberasoftware.base.BaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author renarj
 */
@Configuration
@ComponentScan()
@Import(BaseConfiguration.class)
public class ZWaveConfiguration {
}
