import sys
import json

from bioblend.galaxy import GalaxyInstance

galaxyEnpoint, galaxyAPIKey, wid, inputSource, collectionID, historyID = sys.argv[:1]

gi = GalaxyInstance(url=galaxyEnpoint, key=galaxyAPIKey)
data = {"0": {"src": "hdca", "id": collectionID}}
wfOutput = gi.workflows.invoke_workflow(wid, data, history_id=historyID)  
#with open(output, "w") as f:
#	json.dump(f, wfOutput) 
