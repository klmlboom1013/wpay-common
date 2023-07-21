package com.wpay.common.samp.dto;

import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.samp.enums.MobiliansJobCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Log4j2
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class MobiliansCommand<T extends MobiliansCommand<?>> extends SelfValidating<T>{

   @Getter
   @NotBlank(message = "값이 Null이 될 수 없습니다.")
   @Size(min=10, max=20, message = "길이는 10~20 까지 입니다.")
   private String mid;

   @Getter
   @NotBlank(message = "값이 Null이 될 수 없습니다.")
   @Size(max = 64, message = "길이는 최대 64 자리 입니다.")
   private String wtid;

   @Getter
   @Setter
   private String serverName;

   @Override
   public void validateSelf() {
      super.validateSelf();
   }

   /** API URL @PathVariable "version" 값 검증. */
   public abstract boolean checkVersion(String version);

   /** MobiliansJobCode 리턴 */
   public abstract MobiliansJobCode getMobiliansJobCode();
}
