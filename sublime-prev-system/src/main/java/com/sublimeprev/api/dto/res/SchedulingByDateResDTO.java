package com.sublimeprev.api.dto.res;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.Scheduling;

import lombok.Data;

@Data
public class SchedulingByDateResDTO {

	private Long id;
	private LocalTime hour;
	private List<UserTypeExerciseTodayResDTO> userTypeExercise;
	private SchedulingStatus status;
	private boolean morning;
	private String typeExercise;
	
	private SchedulingByDateResDTO (Scheduling entity,List<UserTypeExerciseTodayResDTO> userTypeExercise) {
		
		this.id = entity.getId();
		this.hour = entity.getTime();
		this.userTypeExercise = userTypeExercise;
		this.status = entity.getStatus();
		this.morning = true;
		this.typeExercise = entity.getTypeExercise().getType();
	}
	
	public static List<SchedulingByDateResDTO> getListByDate(List<Scheduling> listScheduling){
		
		List<SchedulingByDateResDTO> listSchedulingTodayResDTO = new ArrayList<>();
		
		listScheduling.forEach(entity -> {
			
			if(listSchedulingTodayResDTO.isEmpty() || listSchedulingTodayResDTO == null) {
				
				List<UserTypeExerciseTodayResDTO> userTypeExercise = new ArrayList<>();
				UserTypeExerciseTodayResDTO userTypeExerciseTodayResDTO = new UserTypeExerciseTodayResDTO(entity.getUser().getName(), entity.getTypeExercise().getType());
				
				userTypeExercise.add(userTypeExerciseTodayResDTO);
				
				SchedulingByDateResDTO schendulingTemp = new SchedulingByDateResDTO(entity, userTypeExercise);
					
				if(LocalTime.of(12, 00, 00).isBefore(entity.getTime())){
					
					schendulingTemp.setMorning(false);
				}			
				
				listSchedulingTodayResDTO.add(schendulingTemp);
			
			}else {
				
				loop:
				for(int schenduling = 0; schenduling < listSchedulingTodayResDTO.size(); schenduling++) {	
					
					int position = schenduling;
					if(entity.getTime().equals(listSchedulingTodayResDTO.get(schenduling).hour) && entity.getTypeExercise().getType() == listSchedulingTodayResDTO.get(schenduling).typeExercise) {
						
						listSchedulingTodayResDTO.stream().filter(line -> listSchedulingTodayResDTO.get(position).id.equals(line.id)).
						forEach(line -> line.userTypeExercise.add(new UserTypeExerciseTodayResDTO(entity.getUser().getName(),entity.getTypeExercise().getType())));
						
						break loop;
					
					}
					if(schenduling+1 == listSchedulingTodayResDTO.size()){
						
						List<UserTypeExerciseTodayResDTO> userTypeExercise = new ArrayList<>();
						
						UserTypeExerciseTodayResDTO userTypeExerciseTodayResDTO = new UserTypeExerciseTodayResDTO(entity.getUser().getName(), entity.getTypeExercise().getType());
						
						userTypeExercise.add(userTypeExerciseTodayResDTO);
						
						SchedulingByDateResDTO schendulingTemp = new SchedulingByDateResDTO(entity, userTypeExercise);
							
						if(LocalTime.of(12, 00, 00).isBefore(entity.getTime())){
							
							schendulingTemp.setMorning(false);
						}			
						
						listSchedulingTodayResDTO.add(schendulingTemp);
						break loop;
					}				
			    };
			}
			
		});	
		
		return listSchedulingTodayResDTO;
	}
}
